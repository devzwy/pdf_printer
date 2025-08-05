import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("eu.kakde.gradle.sonatype-maven-central-publisher") version "1.0.6"
}

// ------------------------------------
// PUBLISHING TO SONATYPE CONFIGURATION
// ------------------------------------
object Meta {
    val COMPONENT_TYPE = "java" // "java" or "versionCatalog"
    val GROUP = "io.github.devzwy"
    val ARTIFACT_ID = "pdf_printer"
    val VERSION = "1.0.1"
    val PUBLISHING_TYPE = "AUTOMATIC" // USER_MANAGED or AUTOMATIC
    val SHA_ALGORITHMS = listOf("SHA-256", "SHA-512") // sha256 and sha512 are supported but not mandatory. Only sha1 is mandatory but it is supported by default.
    val DESC = "打印pdf文件"
    val LICENSE = "Apache-2.0"
    val LICENSE_URL = "https://opensource.org/licenses/Apache-2.0"
    val GITHUB_REPO = "devzwy/pdf_printer.git"
    val DEVELOPER_ID = "devzwy"
    val DEVELOPER_NAME = "devzwy"
    val DEVELOPER_ORGANIZATION = "devzwy"
    val DEVELOPER_ORGANIZATION_URL = "https://www.y60.net"
}

val sonatypeUsername: String? by project // this is defined in ~/.gradle/gradle.properties
val sonatypePassword: String? by project // this is defined in ~/.gradle/gradle.properties

sonatypeCentralPublishExtension {
    // Set group ID, artifact ID, version, and other publication details
    groupId.set(Meta.GROUP)
    artifactId.set(Meta.ARTIFACT_ID)
    version.set(Meta.VERSION)
    componentType.set(Meta.COMPONENT_TYPE) // "java" or "versionCatalog"
    publishingType.set(Meta.PUBLISHING_TYPE) // USER_MANAGED or AUTOMATIC

    // Set username and password for Sonatype repository
    username.set(System.getenv("SONATYPE_USERNAME") ?: sonatypeUsername)
    password.set(System.getenv("SONATYPE_PASSWORD") ?: sonatypePassword)

    // Configure POM metadata
    pom {
        name.set(Meta.ARTIFACT_ID)
        description.set(Meta.DESC)
        url.set("https://github.com/${Meta.GITHUB_REPO}")
        licenses {
            license {
                name.set(Meta.LICENSE)
                url.set(Meta.LICENSE_URL)
            }
        }
        developers {
            developer {
                id.set(Meta.DEVELOPER_ID)
                name.set(Meta.DEVELOPER_NAME)
                organization.set(Meta.DEVELOPER_ORGANIZATION)
                organizationUrl.set(Meta.DEVELOPER_ORGANIZATION_URL)
            }
        }
        scm {
            url.set("https://github.com/${Meta.GITHUB_REPO}")
            connection.set("scm:git:https://github.com/${Meta.GITHUB_REPO}")
            developerConnection.set("scm:git:https://github.com/${Meta.GITHUB_REPO}")
        }
        issueManagement {
            system.set("GitHub")
            url.set("https://github.com/${Meta.GITHUB_REPO}/issues")
        }
    }
}

repositories {
    //https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/
    //https://oss.sonatype.org/content/repositories/releases/
    maven { url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") }
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
    jcenter()
    maven {
        url = uri("https://maven.aliyun.com/repository/public")
    }
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.apache.pdfbox:pdfbox:3.0.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
