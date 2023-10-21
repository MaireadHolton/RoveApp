package org.wit.rove.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.rove.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "visits.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<RoveModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class RoveJSONStore(private val context: Context) : RoveStore {
    var visits = mutableListOf<RoveModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<RoveModel> {
        logAll()
        return visits
    }

    override fun create(visit: RoveModel) {
        visit.id = generateRandomId()
        visits.add(visit)
        serialize()
    }


    override fun update(visit: RoveModel) {
        // todo
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(visits, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        visits = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        visits.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}