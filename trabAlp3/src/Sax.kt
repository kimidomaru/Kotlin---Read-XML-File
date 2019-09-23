import java.util.*
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

fun main() {
    val saxParse = SaxParser()
    saxParse.parse()
}
class SaxParser {

    @Throws(Exception::class)
    fun parse() {
        val musicGenres = MscMap()
        val factory = SAXParserFactory.newInstance()
        val saxParser = factory.newSAXParser()
        val saxHandler = SAXHandler(musicGenres)
        val start = System.currentTimeMillis()
        saxParser.parse("src/Music.2016.combined.xml", saxHandler)
        val end = System.currentTimeMillis()

        musicGenres.showMap()

        println("Total Time: " + (end - start) + " ms")
    }
}

internal class SAXHandler(genres: MscMap) : DefaultHandler() {
    private val genresMap = genres
    private var musicGenres = genresMap.map

    var insideDatafieldTag650 = false
    var insideSubfieldCodeA = false

    init {
        this.musicGenres = musicGenres
    }

    @Throws(SAXException::class)
    override fun startElement(uri: String, localName: String, qName: String, attributes: Attributes) {
        if (!insideDatafieldTag650) {
            if (qName.equals("datafield", ignoreCase = true) && attributes.getValue("tag").equals("650")) {
                insideDatafieldTag650 = true
            }
        } else if (!insideSubfieldCodeA) {
            if (qName.equals("subfield", ignoreCase = true) && attributes.getValue("code").equals("a")) {
                insideSubfieldCodeA = true
            }
        }
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, localName: String, qName: String) {
        if (insideSubfieldCodeA) {
            if (qName.equals("subfield", ignoreCase = true)) {
                insideSubfieldCodeA = false
            }
        } else if (insideDatafieldTag650) {
            if (qName.equals("datafield", ignoreCase = true)) {
                insideDatafieldTag650 = false
            }
        }
    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray, start: Int, length: Int) {
        if (insideDatafieldTag650 && insideSubfieldCodeA) {
            val genre = String(ch, start, length)

            if (genresMap.existInMap(genre)){
                //Se existir soma 1
                genresMap.countMap(genre)
            }
            else{
                //Adicionando ao mapa
                genresMap.addToMap(genre, 1)
            }

        }
    }
}

class MscMap {
    private val mapa: MutableMap <String, Int> = mutableMapOf()

    val map: Map<String, Int>
        get() = this.mapa

    fun addToMap(key: String, count: Int) {
        mapa[key] = count
    }

    fun existInMap(key: String): Boolean {
        return if (mapa.containsKey(key)) {
            true
        } else false
    }

    fun countMap(key: String) {
        (mapa as java.util.Map<String, Int>).replace(key, mapa[key]!!, (mapa[key]!! + 1))
    }

    fun showMap() {
        val it = mapa.entries.iterator()
        while (it.hasNext()) {
            val entrada = it.next()
            if(entrada.value>5)
                println(entrada.key + " : (" + entrada.value + ")")
        }
    }
}