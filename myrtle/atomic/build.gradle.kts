import java.text.SimpleDateFormat

plugins {
	// Apply the shared build logic from a convention plugin.
	// The shared code is located in `buildSrc/src/main/kotlin/kotlin-jvm.gradle.kts`.
	id("buildsrc.convention.kotlin-jvm")
	application
	// Apply Kotlin Serialization plugin from `gradle/libs.versions.toml`.
	alias(libs.plugins.kotlinPluginSerialization)
	//id("org.jetbrains.kotlin.kapt") version "2.1.21"
	//groovy
}

val timestamp = System.currentTimeMillis()

fun time(format: String):String{
	return SimpleDateFormat(format).format(timestamp)
}

group = "org.myrtle"
version = "${time("yy")}.${time("MM")}-alpha+${time("dd")}+${time("HH")}+j${System.getProperty("java.version")}"
//version = SimpleDateFormat("yy.MM.dd").format(timestamp)

repositories {
	maven {
		url = uri("https://www.jitpack.io")
	}
}


dependencies {
	// Apply the kotlinx bundle of dependencies from the version catalog (`gradle/libs.versions.toml`).
	implementation(libs.bundles.kotlinxEcosystem)
	testImplementation(kotlin("test"))

	implementation("ch.qos.logback:logback-classic:1.5.21")
	implementation("ch.qos.logback:logback-core:1.5.21")
	implementation("org.slf4j:slf4j-api:2.0.17")

	// https://mvnrepository.com/artifact/io.github.cdimascio/dotenv-java
	implementation("io.github.cdimascio:dotenv-java:3.2.0")

	implementation("com.google.code.gson:gson:2.10.1")
	// apache http client
	implementation("org.apache.httpcomponents:httpclient:4.5.14")
	// Hikari
	implementation("com.zaxxer:HikariCP:6.2.1")
	// apache vfs2
	implementation("org.apache.commons:commons-vfs2:2.10.0")
	// apache codec
	// https://mvnrepository.com/artifact/commons-codec/commons-codec
	implementation("commons-codec:commons-codec:1.20.0")

	implementation("org.apache.commons:commons-configuration2:2.13.0")
	implementation("org.yaml:snakeyaml:2.2")
	implementation("com.googlecode.plist:dd-plist:1.24")
	// https://mvnrepository.com/artifact/commons-io/commons-io
	implementation("commons-io:commons-io:2.21.0")
	// sqlite
	// https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
	implementation("org.xerial:sqlite-jdbc:3.51.1.0")
	// https://mvnrepository.com/artifact/com.h2database/h2
	implementation("com.h2database:h2:2.4.240")
	// https://mvnrepository.com/artifact/org.luaj/luaj-jse
	//implementation("org.luaj:luaj-jse:3.0.1")

	// https://mvnrepository.com/artifact/com.electronwill.night-config/toml
	runtimeOnly("com.electronwill.night-config:toml:3.8.3")
	// https://mvnrepository.com/artifact/com.electronwill.night-config/core
	implementation("com.electronwill.night-config:core:3.8.3")
	// https://mvnrepository.com/artifact/com.electronwill.night-config/yaml
	runtimeOnly("com.electronwill.night-config:yaml:3.8.3")
	// https://mvnrepository.com/artifact/com.electronwill.night-config/json
	runtimeOnly("com.electronwill.night-config:json:3.8.3")
	// https://mvnrepository.com/artifact/com.electronwill.night-config/hocon
	implementation("com.electronwill.night-config:hocon:3.8.3")

	// https://mvnrepository.com/artifact/com.typesafe/config
	implementation("com.typesafe:config:1.4.3")

	//implementation(fileTree("source/library"))

	// Source: https://mvnrepository.com/artifact/org.luaj/luaj-jse
	//implementation("org.luaj:luaj-jse:3.0.1")
// Source: https://mvnrepository.com/artifact/org.duckdb/duckdb_jdbc
	implementation("org.duckdb:duckdb_jdbc:1.4.3.0")
	//implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("com.github.artbits:quickio:1.4.1")
	// Source: https://mvnrepository.com/artifact/commons-beanutils/commons-beanutils
	implementation("commons-beanutils:commons-beanutils:1.11.0")

	implementation("tools.jackson.dataformat:jackson-dataformat-toml:3.0.4")
	implementation("tools.jackson.dataformat:jackson-dataformat-yaml:3.0.4")
	implementation("tools.jackson.dataformat:jackson-dataformat-xml:3.0.4")
	implementation("tools.jackson.dataformat:jackson-dataformat-cbor:3.0.4")
	implementation("tools.jackson.dataformat:jackson-dataformat-smile:3.0.4")
	implementation("tools.jackson.dataformat:jackson-dataformat-csv:3.0.4")
	implementation("tools.jackson.dataformat:jackson-dataformat-properties:3.0.4")
	implementation("tools.jackson.dataformat:jackson-dataformat-avro:3.0.4")
	implementation("tools.jackson.dataformat:jackson-dataformat-protobuf:3.0.4")
	implementation("tools.jackson.dataformat:jackson-dataformat-ion:3.0.4")
	// Source: https://mvnrepository.com/artifact/io.openapitools.jackson.dataformat/jackson-dataformat-hal
	implementation("io.openapitools.jackson.dataformat:jackson-dataformat-hal:1.0.9")
	// Source: https://mvnrepository.com/artifact/com.jasonclawson/jackson-dataformat-hocon
	implementation("com.jasonclawson:jackson-dataformat-hocon:1.1.0")
	// Source: https://mvnrepository.com/artifact/org.msgpack/jackson-dataformat-msgpack
	implementation("org.msgpack:jackson-dataformat-msgpack:0.9.11")
	// Source: https://mvnrepository.com/artifact/com.arangodb/jackson-dataformat-velocypack
	implementation("com.arangodb:jackson-dataformat-velocypack:4.6.3")
	// Source: https://mvnrepository.com/artifact/tools.jackson.module/jackson-module-kotlin
	implementation("tools.jackson.module:jackson-module-kotlin:3.0.4")
	// Source: https://mvnrepository.com/artifact/tools.jackson.datatype/jackson-datatype-guava
	implementation("tools.jackson.datatype:jackson-datatype-guava:3.0.4")
	// Source: https://mvnrepository.com/artifact/org.honton.chas.hocon/jackson-dataformat-hocon
	implementation("org.honton.chas.hocon:jackson-dataformat-hocon:1.1.1")
	implementation("com.google.guava:guava:33.5.0-jre")
}

sourceSets {
	main {
		java {
			setSrcDirs(listOf("source/master/platform/java","source/master/platform/all","src/main/java"))
			resources {
				setSrcDirs(listOf("source/master/resources","src/main/resources"))
			}
		}

		kotlin {
			setSrcDirs(listOf("source/master/platform/kotlin","source/master/platform/all","src/main/kotlin"))
			resources {
				setSrcDirs(listOf("source/master/resources","src/main/resources"))
			}
		}

//		groovy {
//			setSrcDirs(listOf("source/master/platform/groovy","source/master/platform/all","src/main/groovy"))
//			resources {
//				setSrcDirs(listOf("source/master/resources","src/main/resources"))
//			}
//		}

		resources {
			setSrcDirs(listOf("source/master/resources","src/main/resources"))
		}
	}

	test {
		java {
			setSrcDirs(listOf("source/measure/platform/java","source/measure/platform/all","src/test/java"))
			resources {
				setSrcDirs(listOf("source/measure/resources","src/test/resources"))
			}
		}
		kotlin {
			setSrcDirs(listOf("source/measure/platform/kotlin","source/measure/platform/all","src/test/kotlin"))
			resources {
				setSrcDirs(listOf("source/measure/resources","src/test/resource"))
			}
		}
//		groovy {
//			setSrcDirs(listOf("source/measure/platform/groovy","source/measure/platform/all","src/test/groovy"))
//			resources {
//				setSrcDirs(listOf("source/measure/resources","src/test/resource"))
//			}
//		}
		resources {
			setSrcDirs(listOf("source/measure/resources","src/test/resource"))
		}
	}
}

tasks.jar{
	manifest {
		attributes["Implementation-Title"] = project.name
		attributes["Implementation-Version"] = project.version
		attributes["Implementation-Vendor"] = "Atomic Energy Computer Science Development Studio (AECS)"
		attributes["Specification-Title"] = project.name
		attributes["Specification-Version"] = project.version
		attributes["Specification-Vendor"] = "Atomic Energy Computer Science Development Studio (AECS)"
		attributes["Created-By"] = "Gradle ${gradle.gradleVersion}"
		//attributes["Build-JDK"] = System.getProperty("java.vendor")
		attributes["Build-JDK-Version"] = System.getProperty("java.version")
		attributes["Build-JDK-Vendor"] = System.getProperty("java.vendor")
		attributes["Build-JDK-Vendor-URL"] = System.getProperty("java.vendor.url")
		attributes["Build-JDK-Class-Version"] = System.getProperty("java.class.version")
		attributes["Build-JDK-Runtime-Name"] = System.getProperty("java.runtime.name")
		attributes["Build-JDK-Runtime-Version"] = System.getProperty("java.runtime.version")
		attributes["Build-JDK-VM-Name"] = System.getProperty("java.vm.name")
		attributes["Build-JDK-VM-Version"] = System.getProperty("java.vm.version")
		attributes["Build-JDK-VM-Vendor"] = System.getProperty("java.vm.vendor")
		attributes["Build-JDK-VM-Specification-Version"] = System.getProperty("java.vm.specification.version")
		attributes["Build-JDK-Specification-Version"] = System.getProperty("java.specification.version")
		attributes["Build-JDK-Specification-Vendor"] = System.getProperty("java.specification.vendor")
		attributes["Build-JDK-Specification-Name"] = System.getProperty("java.specification.name")
		attributes["Build-OS-Name"] = System.getProperty("os.name")
		attributes["Build-OS-Version"] = System.getProperty("os.version")
		attributes["Build-OS-Arch"] = System.getProperty("os.arch")
		attributes["Build-Timestamp"] = timestamp
		attributes["Build-DateTime"] = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp)
		attributes["Build-User"] = System.getProperty("user.name")


		attributes["Class-Path"] = configurations.runtimeClasspath.get().files.joinToString(separator = " ") {
			"lib/${it.name}"
		}
	}
}