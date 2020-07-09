class BadRequestException(reason: String) extends Throwable {
  override def getMessage: String = reason

}
