package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.{ClosedShape, FlowShape}
import akka.stream.scaladsl.{Balance, Broadcast, Flow, GraphDSL, Merge, RunnableGraph, Source}

class UpgradeShop {


  val installUpgrades:Flow[UnfinishedCar,UnfinishedCar,NotUsed] = {
    Flow.fromGraph(GraphDSL.create(){
      implicit b =>
        import GraphDSL.Implicits._

        val balance = b.add(Balance[UnfinishedCar](3))
        val merge = b.add(Merge[UnfinishedCar](3))

        balance.out(0).map(_.installUpgrade(Upgrade.DX)) ~> merge.in(0)
        balance.out(1).map(_.installUpgrade(Upgrade.Sport)) ~> merge.in(1)
        balance.out(2).map(x => x) ~> merge.in(2)

        FlowShape(balance.in,merge.out)

    })
  }

  /**
  val installUpgrades:Flow[UnfinishedCar,UnfinishedCar,NotUsed] = {
     RunnableGraph.fromGraph(GraphDSL.create(){
      implicit builder:GraphDSL.Builder[NotUsed] =>

        import GraphDSL.Implicits._

        //val source = Source[UnfinishedCar]
        val bcast = builder.add(Broadcast[UnfinishedCar](3))
        val merge = builder.add(Merge[UnfinishedCar](3))
        val f1 = Flow[UnfinishedCar].map(x => x.installUpgrade(Upgrade.DX))
        val f2 = Flow[UnfinishedCar].map(x => x.installUpgrade(Upgrade.Sport))
        val f3 = Flow[UnfinishedCar].map(x => x)
        val f4 = Flow[UnfinishedCar]
        bcast ~> f1 ~> merge ~> f4
        bcast ~> f2 ~> merge
        bcast ~> f3 ~> merge
        ClosedShape

    }).run()
  }
   */

}
