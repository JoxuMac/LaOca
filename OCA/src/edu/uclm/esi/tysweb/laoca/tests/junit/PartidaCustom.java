/*
LA OCA - 2017 - Tecnologias y Sistemas Web
Escuela Superior de Informatica de Ciudad Real 

Josue Gutierrez Duran
Sonia Querencia Martin
Enrique Simarro Santamaria
Eduardo Fuentes Garcia De Blas
*/

package edu.uclm.esi.tysweb.laoca.tests.junit;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class PartidaCustom {

	
  private WebDriver driverA,driverB;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	  
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--incognito");
    
    System.setProperty("webdriver.chrome.driver", "/chromedriver.exe");
    driverA = new ChromeDriver(options);
	driverB = new ChromeDriver();	
    baseUrl = "http://localhost:8080/";
    driverA.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driverB.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
   
  }

  @Test
  public void testPartidaCustom() throws Exception {
    driverA.get(baseUrl+"OCA/index.html");
    driverA.findElement(By.xpath("(//button[@id='IngresoLog'])[2]")).click();
    driverA.findElement(By.id("Nombre")).click();
    driverA.findElement(By.id("Nombre")).clear();
    driverA.findElement(By.id("Nombre")).sendKeys("hola");
    driverA.findElement(By.id("pass")).clear();
    driverA.findElement(By.id("pass")).sendKeys("hola");
    driverA.findElement(By.id("IngresoLog")).click();
    driverA.findElement(By.linkText("Jugar")).click();
    //nos conectamos con A y esperamos
    Thread.sleep(1000);
    driverB.get("http://localhost:8080/OCA/index.html");
    driverB.findElement(By.xpath("(//button[@id='IngresoLog'])[2]")).click();
    driverB.findElement(By.id("Nombre")).click();
    driverB.findElement(By.id("Nombre")).clear();
    driverB.findElement(By.id("Nombre")).sendKeys("pepe1");
    driverB.findElement(By.id("pass")).clear();
    driverB.findElement(By.id("pass")).sendKeys("pepe");
    driverB.findElement(By.id("IngresoLog")).click();
    driverB.findElement(By.linkText("Jugar")).click();
    //nos conectamos con B y esperamos para asegurarnos de que los dos estan en partida
    Thread.sleep(1000);
    driverA.findElement(By.id("broadcast")).sendKeys("4","DADO");
    driverA.findElement(By.id("broadcast")).sendKeys("3","DADO");
    driverA.findElement(By.id("broadcast")).sendKeys("3","DADO");
    driverA.findElement(By.id("broadcast")).sendKeys("1","DADO");
    //comprobamos que está en la casilla que le corresponde
    try {
        assertTrue(driverA.findElement(By.id("idcasilla")).getText()=="15");
      } catch (Error e) {
        verificationErrors.append(e.toString());
      }
    driverB.findElement(By.id("broadcast")).sendKeys("1","DADO");
    driverA.findElement(By.id("broadcast")).sendKeys("4","DADO");
    //comprobamos que A está en la casilla de posada
    try {
        assertTrue(driverA.findElement(By.id("idcasilla")).getText()=="19");
      } catch (Error e) {
        verificationErrors.append(e.toString());
      }
    
    driverB.findElement(By.id("broadcast")).sendKeys("4","DADO");
    driverB.findElement(By.id("broadcast")).sendKeys("1","DADO");
    driverB.findElement(By.id("broadcast")).sendKeys("5","DADO");
    driverB.findElement(By.id("broadcast")).sendKeys("3","DADO");
    driverB.findElement(By.id("broadcast")).sendKeys("2","DADO");
    driverB.findElement(By.id("broadcast")).sendKeys("1","DADO"); 
    driverA.findElement(By.id("broadcast")).sendKeys("4","DADO");
    driverA.findElement(By.id("broadcast")).sendKeys("5","DADO");
    driverA.findElement(By.id("broadcast")).sendKeys("5","DADO");
    driverA.findElement(By.id("broadcast")).sendKeys("5","DADO");
    driverA.findElement(By.id("broadcast")).sendKeys("1","DADO");
    driverB.findElement(By.id("broadcast")).sendKeys("6","DADO");
    driverA.findElement(By.id("broadcast")).sendKeys("1","DADO");
    driverB.findElement(By.id("broadcast")).sendKeys("2","DADO");
    //comprobamos que B retrocede 1 y se queda en la casilla que estaba
    try {
        assertTrue(driverB.findElement(By.id("idcasilla")).getText()=="62");
      } catch (Error e) {
        verificationErrors.append(e.toString());
      } 
    driverA.findElement(By.id("broadcast")).sendKeys("4","DADO");
    driverB.findElement(By.id("broadcast")).sendKeys("1","DADO");
    //comprobamos que B está en la ultima casilla
    try {
        assertTrue(driverB.findElement(By.id("idcasilla")).getText()=="63");
      } catch (Error e) {
        verificationErrors.append(e.toString());
      }
    
    
  }

  @After
  public void tearDown() throws Exception {
	  driverA.quit();
	  driverB.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
    	driverA.findElement(by);
    	driverB.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
    	driverA.switchTo().alert();
    	driverB.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driverA.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
