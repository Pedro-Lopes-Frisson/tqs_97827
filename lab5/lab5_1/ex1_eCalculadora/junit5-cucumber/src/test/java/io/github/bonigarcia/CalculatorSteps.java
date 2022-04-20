/*
 * (C) Copyright 2017 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatorSteps {

    static final Logger log = getLogger(lookup().lookupClass());

    private Calculator calc;

    @Given("a calculator I just turned on")
    public void setup() {
        calc = new Calculator();
    }

      @When("I add {int} and {int}")
      public void i_add_and(Integer int1, Integer int2) {
        log.debug("Adding {} and {}", int1, int2);
        calc.push(int1);
        calc.push(int2);
        calc.push("+");
      }

      @When("I substract {int} to {int}")
      public void i_substract_to(Integer int1, Integer int2) {
        log.debug("Substracting {} and {}", int1, int2);
        calc.push(int1);
        calc.push(int2);
        calc.push("-");
      }

      @Then("the result is {int}")
      public void the_result_is(double expected) {
          Number value = calc.value();
          log.debug("Result: {} (expected {})", value, expected);
          assertEquals(expected, value);
      }

}
