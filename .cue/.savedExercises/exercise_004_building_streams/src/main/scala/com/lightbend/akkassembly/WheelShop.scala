package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}

class WheelShop {

  val wheels:Source[Wheel,NotUsed] = Source.repeat(
    new Wheel
  )

  val installWheels:Flow[UnfinishedCar,UnfinishedCar,NotUsed] = {
    Flow[UnfinishedCar].zip{
      wheels.grouped(4
      )
    }.map{x =>
      x._1.installWheels(x._2)
    }
  }

}
