import sbt._

object PackagingTypePlugin extends AutoPlugin {
  override val buildSettings: List[Nothing] = {
    sys.props += "packaging.type" -> "jar"
    Nil
  }
}
