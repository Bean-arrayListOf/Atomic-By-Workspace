import java.text.SimpleDateFormat

plugins {
	// Apply the shared build logic from a convention plugin.
	// The shared code is located in `buildSrc/src/main/kotlin/kotlin-jvm.gradle.kts`.
	id("buildsrc.convention.kotlin-jvm")
	application
	// Apply Kotlin Serialization plugin from `gradle/libs.versions.toml`.
	alias(libs.plugins.kotlinPluginSerialization)
	groovy
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

	//implementation(project(":atomic"))
	implementation(project(":citrus"))

	// https://mvnrepository.com/artifact/io.hotmoka/toml4j
	implementation("io.hotmoka:toml4j:0.7.3")
	// https://mvnrepository.com/artifact/com.typesafe/config
	implementation("com.typesafe:config:1.4.3")
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

tasks.jar {
	manifest {
		attributes["Implementation-Title"] = project.name
		attributes["Implementation-Version"] = project.version
		attributes["Implementation-Vendor"] = "Myrtle Development Studio"
		attributes["Specification-Title"] = project.name
		attributes["Specification-Version"] = project.version
		attributes["Specification-Vendor"] = "Myrtle Development Studio"
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