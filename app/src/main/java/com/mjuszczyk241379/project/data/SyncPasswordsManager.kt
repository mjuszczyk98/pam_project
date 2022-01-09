package com.mjuszczyk241379.project.data

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.*
import com.mongodb.client.model.Updates
import org.bson.Document
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import java.util.*

class SyncPasswordsManager(
    private val passwordRepository: PasswordRepository,
    private val syncDateRepository: SyncDateRepository
) {

    private val _uri = "mongodb://10.0.2.2:27017"
    private val _dbName = "pam_db"
    private val _tableName = "passwords"

    suspend fun syncPasswords(lastSyncDate: Long) {
        val collection = getMongoCollection()

        val localPasswords = passwordRepository.readAll().map {
            it.id to it
        }.toMap()
        val remotePasswords = collection.find().map {
            val password = documentToPassword(it)
            password.id to password
        }.toMap()

        val toAddLocally = LinkedList<Password>()
        val toEditLocally = LinkedList<Password>()
        val toRemoveLocally = LinkedList<Password>()
        val toAddRemote = LinkedList<Password>()
        val toEditRemote = LinkedList<Password>()
        val toRemoveRemote = LinkedList<Password>()

        localPasswords.values.forEach {
            if (remotePasswords[it.id] == null) {
                if (it.lastEditDate ?: 0 >= lastSyncDate) {
                    toAddRemote.add(it)
                }
                else {
                    toRemoveLocally.add(it)
                }
            }
            else {
                if (it.lastEditDate ?: 0 < remotePasswords[it.id]!!.lastEditDate ?: 0) {
                    toEditLocally.add(it)
                }
                else if (it.lastEditDate ?: 0 > remotePasswords[it.id]!!.lastEditDate ?: 0) {
                    toEditRemote.add(it)
                }
            }
        }

        remotePasswords.values.forEach {
            if (localPasswords[it.id] == null) {
                if (it.lastEditDate ?: 0 >= lastSyncDate) {
                    toAddLocally.add(it)
                }
                else {
                    toRemoveRemote.add(it)
                }
            }
            // else was done previously
        }

        // Remote update
        toRemoveRemote.forEach {
            collection.deleteOne(`in`("_id", passwordToDocument(it)["_id"]))
        }
        toEditRemote.forEach {
            collection.updateOne(eq("_id", passwordToDocument(it)["_id"]), passwordToBsonUpdate(it))
        }
        toAddRemote.forEach {
            collection.insertOne(passwordToDocument(it))
        }

        // Local update
        toRemoveLocally.forEach {
            passwordRepository.delete(it)
        }
        toEditLocally.forEach {
            passwordRepository.update(it)
        }
        toAddLocally.forEach {
            passwordRepository.insert(it)
        }

        syncDateRepository.syncDateInsert(SyncDate(0, System.currentTimeMillis()))
    }

    private fun getMongoCollection(): MongoCollection<Document> {
        val mongoUri = MongoClientURI(_uri)
        val mongoClient = MongoClient(mongoUri)
        val db = mongoClient.getDatabase(_dbName)
        return db.getCollection(_tableName)
    }


    private fun passwordToDocument(password: Password): Document {
        val tmpDocument = Document()
        tmpDocument["id"] = password.id
        tmpDocument["name"] = password.name
        tmpDocument["login"] = password.login
        tmpDocument["password"] = password.password
        tmpDocument["note"] = password.note
        tmpDocument["lastEditDate"] = password.lastEditDate
        tmpDocument["_id"] = if (password._id == null) ObjectId() else ObjectId(password._id)
        return tmpDocument
    }

    private fun documentToPassword(document: Document): Password {
        return Password(
            document.getInteger("id"),
            document.getString("name"),
            document.getString("login"),
            document.getString("password"),
            document.getString("note"),
            document.getLong("lastEditDate"),
            document.getObjectId("_id")?.toString()
        )
    }

    private fun passwordToBsonUpdate(password: Password): Bson {
        return Updates.combine(
            Updates.set("id", password.id),
            Updates.set("name", password.name),
            Updates.set("login", password.login),
            Updates.set("password", password.password),
            Updates.set("note", password.note),
        )
    }
}