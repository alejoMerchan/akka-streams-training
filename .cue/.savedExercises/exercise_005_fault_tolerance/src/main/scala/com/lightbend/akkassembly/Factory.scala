package com.lightbend.akkassembly

import akka.stream.Materializer
import akka.stream.scaladsl.{Keep, Sink}

import scala.concurrent.Future

class Factory(bodyShop: BodyShop,paintShop: PaintShop,
              engineShop: EngineShop,wheelShop: WheelShop,qualityAssurance: QualityAssurance)(implicit mat:Materializer) {

  def orderCars(quantity:Int):Future[Seq[Car]] = {
    //val y = bodyShop.cars.grouped(quantity).mapConcat(x => x).via(paintShop.paint).via(engineShop.installEngine).
    bodyShop.cars.via(paintShop.paint).via(engineShop.installEngine).
      via(wheelShop.installWheels).via(qualityAssurance.inspect).take(quantity).toMat(Sink.seq[Car])(Keep.right).run()

  }


}
