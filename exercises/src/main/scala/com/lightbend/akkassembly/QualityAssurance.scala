package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.{ActorAttributes, Supervision}
import akka.stream.scaladsl.Flow
import com.lightbend.akkassembly.QualityAssurance.CarFailedInspection

object QualityAssurance{
  case class CarFailedInspection(car:UnfinishedCar) extends IllegalStateException("!! exception !!")
}
class QualityAssurance {

  val decider: Supervision.Decider = {
    case exception:CarFailedInspection =>
      println(s"CarFailedInspection ${exception.getMessage}")
      Supervision.Resume
  }

  val inspect:Flow[UnfinishedCar,Car,NotUsed] = {
      Flow[UnfinishedCar].collect{
        case x if !x.color.isEmpty && !x.engine.isEmpty && x.wheels.size == 4 => new Car(SerialNumber(),x.color.get,x.engine.get,x.wheels,x.upgrade)
        case x => throw CarFailedInspection(x)
      }.withAttributes{
        ActorAttributes.supervisionStrategy(decider)
      }
  }

}
