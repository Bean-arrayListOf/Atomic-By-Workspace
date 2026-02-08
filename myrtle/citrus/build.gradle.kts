import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat

plugins {
	// Apply the shared build logic from a convention plugin.
	// The shared code is located in `buildSrc/src/main/kotlin/kotlin-jvm.gradle.kts`.
	id("buildsrc.convention.kotlin-jvm")
	application
	// Apply the Application plugin to add support for building an executable JVM application.
	//application
	alias(libs.plugins.kotlinPluginSerialization)
	groovy
	signing
	idea

	id("com.github.johnrengelman.shadow") version "8.1.1"
}

val timestamp = System.currentTimeMillis()

group = "org.myrtle"
version = SimpleDateFormat("yy.MM.dd.HH").format(timestamp)

repositories {
	maven {
		url = uri("https://www.jitpack.io")
	}
}

dependencies {
	// Apply the kotlinx bundle of dependencies from the version catalog (`gradle/libs.versions.toml`).
	implementation(libs.bundles.kotlinxEcosystem)
	testImplementation(kotlin("test"))

	implementation(project(":atomic"))

	implementation("ch.qos.logback:logback-classic:1.5.15")
	implementation("ch.qos.logback:logback-core:1.5.15")
	implementation("org.slf4j:slf4j-api:2.0.12")
	// https://mvnrepository.com/artifact/ch.qos.logback.db/logback-core-db
	implementation("ch.qos.logback.db:logback-core-db:1.2.11.1")
	// https://mvnrepository.com/artifact/ch.qos.logback.db/logback-classic-db
	implementation("ch.qos.logback.db:logback-classic-db:1.2.11.1")
// https://mvnrepository.com/artifact/com.google.jimfs/jimfs
	testImplementation("com.google.jimfs:jimfs:1.3.1")
	implementation("org.codehaus.janino:janino:3.1.12")

	implementation("com.zaxxer:HikariCP:6.2.1")

	implementation("com.google.zxing:core:3.5.2")
	implementation("com.google.zxing:javase:3.5.2")
	implementation("com.twelvemonkeys.imageio:imageio-core:3.10.1")
	implementation("com.twelvemonkeys.imageio:imageio-webp:3.10.1")
	implementation("com.twelvemonkeys.imageio:imageio-tiff:3.10.1")
	// https://mvnrepository.com/artifact/jmagick/jmagick
	implementation("jmagick:jmagick:6.6.9")

	// https://mvnrepository.com/artifact/org.mybatis/mybatis
	implementation("org.mybatis:mybatis:3.5.18")

	implementation("org.apache.commons:commons-exec:1.4.0")

	implementation("org.apache.commons:commons-compress:1.27.1")
	// https://mvnrepository.com/artifact/org.apache.commons/commons-compress
	implementation("org.apache.commons:commons-compress:1.28.0")
	// https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
	implementation("org.apache.commons:commons-lang3:3.18.0")
	// https://mvnrepository.com/artifact/commons-net/commons-net
	implementation("commons-net:commons-net:3.12.0")
	// https://mvnrepository.com/artifact/commons-io/commons-io
	implementation("commons-io:commons-io:2.21.0")
	// https://mvnrepository.com/artifact/org.apache.commons/commons-vfs2
	implementation("org.apache.commons:commons-vfs2:2.10.0")

	implementation("net.java.dev.jna:jna:5.14.0")
	implementation("net.java.dev.jna:jna-platform:5.15.0")

	implementation("org.apache.groovy:groovy-all:5.0.0-alpha-9")

	implementation("com.google.code.gson:gson:2.10.1")
	// https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
	implementation("com.fasterxml.jackson.core:jackson-core:2.18.0")

	implementation("com.h2database:h2:2.3.232")

	implementation("io.hotmoka:toml4j:0.7.3")

	implementation("org.yaml:snakeyaml:2.2")

	implementation("me.tongfei:progressbar:0.10.0")

	implementation("org.apache.hadoop:hadoop-client:3.3.6")

	implementation("org.mapdb:mapdb:3.0.10")

	// mongodb
	implementation("org.mongodb:mongodb-driver-sync:5.2.0")

	compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
	runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
	compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.8.1")
	runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.8.1")

	runtimeOnly("com.mysql:mysql-connector-j:9.3.0")

	implementation("org.bouncycastle:bcprov-jdk18on:1.77")
	implementation("org.bouncycastle:bcpkix-jdk18on:1.77")

	implementation("com.google.guava:guava:33.4.8-jre")
	// sqlite3
	implementation("org.xerial:sqlite-jdbc:3.46.1.3")

	// https://mvnrepository.com/artifact/org.pf4j/pf4j
	implementation("org.pf4j:pf4j:3.12.0")
	// https://mvnrepository.com/artifact/commons-codec/commons-codec
	implementation("commons-codec:commons-codec:1.20.0")
	implementation("org.duckdb:duckdb_jdbc:1.1.3")

	// https://mvnrepository.com/artifact/redis.clients/jedis
	implementation("redis.clients:jedis:5.2.0")

	// https://mvnrepository.com/artifact/io.r2dbc/r2dbc-spi
	implementation("io.r2dbc:r2dbc-spi:1.0.0.RELEASE")
	// https://mvnrepository.com/artifact/io.r2dbc/r2dbc-pool
	implementation("io.r2dbc:r2dbc-pool:1.0.2.RELEASE")
	implementation("io.asyncer:r2dbc-mysql:1.4.1")

	implementation("org.apache.derby:derby:10.17.1.0")

	// kryo
	implementation("com.esotericsoftware:kryo:5.6.2")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.8.1")

	// https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-toml
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
	implementation("org.jetbrains.exposed:exposed-core:1.0.0-rc-3")
	implementation("org.jetbrains.exposed:exposed-jdbc:1.0.0-rc-3")

	// Apache Commons Compress 解压缩bytes
	implementation("org.apache.commons:commons-compress:1.21")
	// https://mvnrepository.com/artifact/org.apache.commons/commons-configuration2
	implementation("org.apache.commons:commons-configuration2:2.13.0")
	implementation("org.yaml:snakeyaml:2.2")

	// https://mvnrepository.com/artifact/com.electronwill.night-config/toml
	implementation("com.electronwill.night-config:toml:3.8.3")
	// https://mvnrepository.com/artifact/com.electronwill.night-config/core
	implementation("com.electronwill.night-config:core:3.8.3")
	// https://mvnrepository.com/artifact/com.electronwill.night-config/yaml
	implementation("com.electronwill.night-config:yaml:3.8.3")
	// https://mvnrepository.com/artifact/com.electronwill.night-config/json
	implementation("com.electronwill.night-config:json:3.8.3")
	// https://mvnrepository.com/artifact/com.electronwill.night-config/hocon
	implementation("com.electronwill.night-config:hocon:3.8.3")
	//implementation("com.github.artbits:quickio:1.4.1")
	// Source: https://mvnrepository.com/artifact/com.orientechnologies/orientdb-core
	//implementation("com.orientechnologies:orientdb-core:3.2.49")

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

		groovy {
			setSrcDirs(listOf("source/master/platform/groovy","source/master/platform/all","src/main/groovy"))
			resources {
				setSrcDirs(listOf("source/master/resources","src/main/resources"))
			}
		}

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
		groovy {
			setSrcDirs(listOf("source/measure/platform/groovy","source/measure/platform/all","src/test/groovy"))
			resources {
				setSrcDirs(listOf("source/measure/resources","src/test/resource"))
			}
		}
		resources {
			setSrcDirs(listOf("source/measure/resources","src/test/resource"))
		}
	}
}

tasks.jar{
	dependsOn(tasks.classes)
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
val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
	freeCompilerArgs.set(listOf("-XXLanguage:+NestedTypeAliases"))
}