package org.myrtle.atomic

import tools.jackson.core.json.JsonFactory
import tools.jackson.databind.ObjectMapper
import tools.jackson.dataformat.toml.TomlFactory
import tools.jackson.dataformat.yaml.YAMLFactory

typealias ErrorOutPutEvents = (Exception) -> Unit

object ErrorKit {
    @JvmStatic
    private val soutEvents: Map<String, ErrorOutPutEvents>
    @JvmStatic
    private val jsonFactory = ObjectMapper(JsonFactory())
    @JvmStatic
    private val tomlFactory = ObjectMapper(TomlFactory())
    @JvmStatic
    private val yaml = ObjectMapper(YAMLFactory())
    @JvmStatic
    private var outputMode: String = "Console.ErrorOutput"

    init {
        this.soutEvents = hashMapOf<String, ErrorOutPutEvents>()

        this.soutEvents["Console.ErrorOutput"] = { e -> e.printStackTrace() }
        this.soutEvents["Console.JsonOutput"] = { e -> System.err.println(jsonFactory.writerWithDefaultPrettyPrinter().writeValueAsString(e)) }
        this.soutEvents["Console.TomlOutput"] = { e -> System.err.println(tomlFactory.writerWithDefaultPrettyPrinter().writeValueAsString(e)) }
        this.soutEvents["Console.YamlOutput"] = { e -> System.err.println(yaml.writerWithDefaultPrettyPrinter().writeValueAsString(e))  }
    }

    fun out(e: Exception){
        val event = soutEvents[outputMode]

        if (event==null) {
            e.printStackTrace()
            return
        }

        event.invoke(e)
    }
}