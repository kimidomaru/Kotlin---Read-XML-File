import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.File
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory


fun main() {

    try {

        val fXmlFile = File("src/Music.2016.combined-lite.xml")
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()
        val doc = dBuilder.parse(fXmlFile)

        val mapa = MapaMusicas()
        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.documentElement.normalize()

        val nodeList = doc.getElementsByTagName("datafield")

        println("----------------------------")

        var contador = 0
        val tempoIni = System.currentTimeMillis().toDouble()
        for (i in 0 until nodeList.length) {
            val node = nodeList.item(i)

            if (node.nodeType === Node.ELEMENT_NODE) {
                val eElement = node as Element
                if (eElement.hasAttribute("tag") && eElement.getAttribute("tag").equals("650")) {
                    val e2 = eElement.getElementsByTagName("subfield").item(0) as Element
                    if (e2.hasAttribute("code") && e2.getAttribute("code").equals("a")) {
                        val nomeGenero = e2.getTextContent()
                        if (mapa.existInMap(nomeGenero)) {
                            mapa.countMap(nomeGenero)
                        } else {
                            mapa.addToMap(nomeGenero, 1)
                        }
                        contador++
                    }
                }
            }

        }
        mapa.showMap()
        println("Musicas encontradas: $contador")
        val tempoFinal = System.currentTimeMillis().toDouble()
        val tempoTot = tempoFinal - tempoIni
        println("Tempo de Execução(Ms): $tempoTot")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


class MapaMusicas {
    private val mapa = HashMap<String, Int>()

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
        //Set st = (Set) mapa.entrySet();
        val it = mapa.entries.iterator()
        while (it.hasNext()) {
            val entrada = it.next()
            println(entrada.key + " : (" + entrada.value + ")")
        }
    }
}