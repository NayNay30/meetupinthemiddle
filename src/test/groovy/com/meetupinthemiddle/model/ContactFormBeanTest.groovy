package com.meetupinthemiddle.model

import org.junit.Test

import javax.validation.Validation

import static org.hamcrest.Matchers.*
import static org.junit.Assert.assertThat
//Test probably not required, but first time I've used the validation
// in anger, so wanted to test out!
class ContactFormBeanTest {
  def validator = Validation
      .buildDefaultValidatorFactory()
      .getValidator()

  @Test
  void testValid() {
    def bean = aValidFormBean()
    def constraintViolations = validator.validate(bean).toList()

    assertThat(constraintViolations, hasSize(0))
  }

  @Test
  void testInvalidEmail() {
    def bean = aValidFormBean()
    bean.email = "invalid"
    def constraintViolations = validator.validate(bean).toList()

    assertThat(constraintViolations, hasSize(1))
    assertThat(constraintViolations.first().message, stringContainsInOrder(["email", "invalid"]))

  }

  @Test
  void testNullMessage() {
    def bean = aValidFormBean()
    bean.message = null
    def constraintViolations = validator.validate(bean).toList()

    assertThat(constraintViolations, hasSize(1))
    assertThat(constraintViolations.first().message, containsString("message"))
  }

  @Test
  void testNullSubject() {
    def bean = aValidFormBean()
    bean.subject = null
    def constraintViolations = validator.validate(bean).toList()

    assertThat(constraintViolations, hasSize(1))
    assertThat(constraintViolations.first().message, containsString("subject"))
  }

  @Test
  void testNullName() {
    def bean = aValidFormBean()
    bean.name = null
    def constraintViolations = validator.validate(bean).toList()

    assertThat(constraintViolations, hasSize(1))
    assertThat(constraintViolations.first().message, containsString("name"))
  }

  private aValidFormBean() {
    ContactFormBean.builder()
        .name("Sam")
        .email("sam_lukes@icloud.com")
        .sendCopy(false)
        .subject("Foo")
        .message("Bar")
        .build()
  }
}