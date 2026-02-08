import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.avro.AvroMapper
import com.fasterxml.jackson.dataformat.cbor.databind.CBORMapper
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.ion.IonObjectMapper
import com.fasterxml.jackson.dataformat.protobuf.ProtobufMapper
import com.fasterxml.jackson.dataformat.smile.databind.SmileMapper
import com.fasterxml.jackson.dataformat.toml.TomlMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import org.codehaus.janino.Scanner

import java.nio.charset.Charset
import java.util.concurrent.ThreadLocalRandom

/**
 * 默认编码
 * @return Charset
 */
Charset encode(){
	return Charset.defaultCharset()
}

/**
 * 路径分隔符
 * @return String
 */
String pathSeparator(){
	return File.pathSeparator
}

/**
 * 换行符
 * @return String
 */
String lineSeparator(){
	return System.lineSeparator()
}

/**
 * 路径分隔符字符
 * @return char
 */
char pathSeparatorChar(){
	return File.pathSeparatorChar
}

Properties osProperties(){
	return System.getProperties()
}

String separator(){
	return File.separator
}

Scanner scanner(){
	return new Scanner(System.in)
}

def classPath(){
	ArrayList<String> cp = new ArrayList<String>()
	cp.addAll(System.getProperty("java.class.path").split(File.pathSeparator))
	return cp
}

Map<String,String> env(){
	return System.getenv()
}

ClassLoader classLoader(){
	return Thread.currentThread().contextClassLoader
}

Runtime runtime(){
	return Runtime.getRuntime()
}

String toLowerCase(String str){
	return str.toLowerCase()
}

String toUpperCase(String str){
	return str.toUpperCase()
}

def charTable(){
	return Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','0')
}

Random random(){
	return ThreadLocalRandom.current()
}

int execute(String command, PrintStream out, PrintStream err){
	def process = command.execute()
	process.in.eachLine {
		out.println(it)
	}
	process.err.eachLine {
		err.println(it)
	}
	return process.waitFor()

}

ObjectMapper serializeXML(){
	ObjectMapper mapper = new XmlMapper()
	mapper.enable(SerializationFeature.INDENT_OUTPUT)
	return mapper
}

ObjectMapper serializeJson(){
	ObjectMapper mapper = new JsonMapper()
	mapper.enable(SerializationFeature.INDENT_OUTPUT)
	return mapper
}

ObjectMapper serializeToml(){
	ObjectMapper mapper = new TomlMapper()
	mapper.enable(SerializationFeature.INDENT_OUTPUT)
	return mapper
}

ObjectMapper serializeYaml(){
	ObjectMapper mapper = new YAMLMapper()
	mapper.enable(SerializationFeature.INDENT_OUTPUT)
	return mapper
}

ObjectMapper serializeCbor(){
	ObjectMapper mapper = new CBORMapper()
	mapper.enable(SerializationFeature.INDENT_OUTPUT)
	return mapper
}

ObjectMapper serializeSmile(){
	ObjectMapper mapper = new SmileMapper()
	mapper.enable(SerializationFeature.INDENT_OUTPUT)
	return mapper
}

ObjectMapper serializeCsv(){
	ObjectMapper mapper = new CsvMapper()
	mapper.enable(SerializationFeature.INDENT_OUTPUT)
	return mapper
}

ObjectMapper serializeAvro(){
	ObjectMapper mapper = new AvroMapper()
	//mapper.schemaFor()
	mapper.enable(SerializationFeature.INDENT_OUTPUT)
	return mapper
}

ObjectMapper serializeProtobuf(){
	ObjectMapper mapper = new ProtobufMapper()
	mapper.enable(SerializationFeature.INDENT_OUTPUT)
	return mapper
}

ObjectMapper serializeIon(){
	IonObjectMapper mapper = new IonObjectMapper()
	mapper.enable(SerializationFeature.INDENT_OUTPUT)
	return mapper
}