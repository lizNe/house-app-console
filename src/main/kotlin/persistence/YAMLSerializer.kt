import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import models.House
import persistence.Serializer
import java.io.File

class YAMLSerializer(private val file: File) : Serializer {

    @Throws(Exception::class)
    override fun read(): Any {
        /*   val inputStream: InputStream = FileInputStream(file)
           val yaml = Yaml(Constructor(ArrayList<Note>()::class.java))
           val obj: ArrayList<Note> = yaml.load(inputStream)

         */
        val objectMapper = ObjectMapper(YAMLFactory())
        val obj: Any = objectMapper.readValue(file, ArrayList<House>()::class.java)
        return arrayListOf(obj) as ArrayList<House>
    }

    @Throws(Exception::class)
    override fun write(obj: Any?) {
        /*val writer = PrintWriter(file)
        val options = DumperOptions()
        options.indent = 2
        options.isPrettyFlow = true
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        val yaml = Yaml(options)
        yaml.dump(obj, writer)

         */
        val objectMapper = ObjectMapper(YAMLFactory())
        objectMapper.writeValue(file,obj)
    }
}