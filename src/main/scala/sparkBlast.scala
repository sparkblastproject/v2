/* sparkBlast.scala code to execute Blast on cluster of Spark*/
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
object sparkBlast {
  def main(args: Array[String]) {
  val conf2 = new SparkConf().setAppName("Spark Blast")
    val sc = new SparkContext(conf2)
    val splits = args(0)
    val conf = new Configuration
    
    /* Set delimiter to split file in correct local*/
    conf.set("textinputformat.record.delimiter", ">")
    val script = args(1)
    val dataset = sc.newAPIHadoopFile(args(2), classOf[TextInputFormat], classOf[LongWritable], classOf[Text],conf)
    val mapDataset =  dataset.map(x => x._2.toString)
    
    /* Replace first character to add simbol '>'*/
    val parte2 = mapDataset.map(x=>x.replaceFirst("gi|",">gi|"))
    val parte3 = parte2.map(x=>x.replaceFirst("sp|",">sp|"))
    val parte4 = parte3.map(x=>x.replaceFirst("tr|",">tr|"))
    
    /* Option to repartition file in splits defined on 'val nos'*/
    val repartitionDataset = parte4.repartition(splits.toInt)
    repartitionDataset.pipe(script).saveAsTextFile(args(3))
    sc.stop
}
