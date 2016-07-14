/* SimpleApp.scala */
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
object SparkBlast {
  def main(args: Array[String]) {
    val conf2 = new SparkConf().setAppName("Spark Blast")
    val sc = new SparkContext(conf2)
    val nos = args(0)
    val conf = new Configuration
    conf.set("textinputformat.record.delimiter", ">")
    conf.set("mapreduce.input.fileinputformat.split.maxsize", nos.toString)
    val script = args(1)
    val dataset = sc.newAPIHadoopFile(args(2), classOf[TextInputFormat], classOf[LongWritable], classOf[Text], 
                conf).map(x => x._2.toString).map(x=>x.replace("gi|",">gi|")).pipe(script).
                saveAsTextFile(args(3))
    sc.stop
  }
}
