package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;

  private TorpedoStore mockprimary;
  private TorpedoStore mocksecondary;

  @BeforeEach
  public void init(){
    mockprimary = Mockito.mock(TorpedoStore.class);
    mocksecondary = Mockito.mock(TorpedoStore.class);
    this.ship = new GT4500(mockprimary,mocksecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockprimary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verify(mockprimary,times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockprimary.fire(1)).thenReturn(true);
    when(mocksecondary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    verify(mockprimary,times(1)).fire(1);
    verify(mocksecondary,times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Secondary_Failure(){

    when(mockprimary.fire(1)).thenReturn(true);
    when(mocksecondary.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertEquals(false,result);

    verify(mockprimary,times(1)).fire(1);
    verify(mocksecondary,times(1)).fire(1);

  }

  @Test
  public void fireTorpedo_Single_Both_Success(){
    when(mockprimary.fire(1)).thenReturn(true);
    when(mocksecondary.fire(1)).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);
    var result = ship.fireTorpedo(FiringMode.SINGLE);


    assertEquals(true, result);


    verify(mockprimary,times(1)).fire(1);
    verify(mocksecondary,times(1)).fire(1);

  }

  @Test
  public void fireTorpedo_Single_Both_Secondary_Empty_Success(){
    when(mockprimary.fire(1)).thenReturn(true);
    when(mocksecondary.isEmpty()).thenReturn(true);
    when(mocksecondary.fire(1)).thenReturn(false);

    ship.fireTorpedo(FiringMode.SINGLE);
    var result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);


    verify(mockprimary,times(2)).fire(1);
    verify(mocksecondary,times(0)).fire(1);

  }

  @Test
  public void fireTorpedo_Single_Both_Primary_Empty_Success(){
    when(mockprimary.fire(1)).thenReturn(false);
    when(mockprimary.isEmpty()).thenReturn(true);
    when(mocksecondary.fire(1)).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);
    var result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);


    verify(mockprimary,times(0)).fire(1);
    verify(mocksecondary,times(2)).fire(1);

  }

  @Test
  public void fireTorpedo_Single_Both_Empty_Primary_First_Failure(){
    when(mockprimary.fire(1)).thenReturn(false);
    when(mockprimary.isEmpty()).thenReturn(true);
    when(mocksecondary.fire(1)).thenReturn(false);
    when(mocksecondary.isEmpty()).thenReturn(true);

    var result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false, result);


    verify(mockprimary,times(0)).fire(1);
    verify(mocksecondary,times(0)).fire(1);

  }

  @Test
  public void fireTorpedo_Single_Both_Empty_Secondary_First_Failure(){
    when(mockprimary.fire(1)).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);
    when(mockprimary.fire(1)).thenReturn(false);
    when(mockprimary.isEmpty()).thenReturn(true);
    when(mocksecondary.fire(1)).thenReturn(false);
    when(mocksecondary.isEmpty()).thenReturn(true);

    var result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false, result);


    verify(mockprimary,times(1)).fire(1);
    verify(mocksecondary,times(0)).fire(1);

  }

  @Test
  public void fireTorpedo_All_Empty_Failure(){
    when(mockprimary.fire(1)).thenReturn(false);
    when(mockprimary.isEmpty()).thenReturn(true);
    when(mocksecondary.fire(1)).thenReturn(false);
    when(mocksecondary.isEmpty()).thenReturn(true);

    var result = ship.fireTorpedo(FiringMode.ALL);

    assertEquals(false, result);

    verify(mockprimary,times(0)).fire(1);
    verify(mocksecondary,times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Second_Empty_Failure(){
    when(mockprimary.fire(1)).thenReturn(false);
    when(mockprimary.isEmpty()).thenReturn(false);
    when(mocksecondary.fire(1)).thenReturn(false);
    when(mocksecondary.isEmpty()).thenReturn(true);

    var result = ship.fireTorpedo(FiringMode.ALL);

    assertEquals(false, result);

    verify(mockprimary,times(0)).fire(1);
    verify(mocksecondary,times(0)).fire(1);
  }



  @Test
  public void fireLaser_Failure(){
    var result = ship.fireLaser(FiringMode.SINGLE);
    assertEquals(false, result);    
  }



}
