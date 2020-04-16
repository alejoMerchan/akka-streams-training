package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.Flow

class QualityAssurance {

  val inspect:Flow[UnfinishedCar,Car,NotUsed] = {
      Flow[UnfinishedCar].collect{
        case x if !x.color.isEmpty && !x.engine.isEmpty && x.wheels.size == 4 => new Car(SerialNumber(),x.color.get,x.engine.get,x.wheels,None)
      }
  }

}
