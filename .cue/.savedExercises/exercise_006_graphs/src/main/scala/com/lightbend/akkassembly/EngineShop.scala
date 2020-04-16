package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}

class EngineShop(shipmentSize:Int) {

  val shipments:Source[Shipment,NotUsed] = Source.cycle{
    () => Iterator.continually( Shipment(Seq.fill(shipmentSize)(Engine()).to[collection.immutable.Seq]))
  }

  val engines:Source[Engine,NotUsed] = {
    shipments.mapConcat(eng => eng.engines)
  }

  val installEngine:Flow[UnfinishedCar,UnfinishedCar,NotUsed] = {
    Flow[UnfinishedCar].zip(engines).map(x => x._1.installEngine(x._2))
  }

}
