create index ServicePointCity on ServicePoint(city);
-- queries the address, starttime and endtime of the servicepoints in the same city as a particular user    
-- e.g. SELECT streetaddr,starttime,endtime
--      FROM ServicePoint
--      WHERE ServicePoint.city IN (SELECT Address.city FROM Address WHERE userid=5);

create index DeliverTime on Deliver_To(TimeDelivered) cluster;
-- queries the ordernumbers that are to be delivered to a particular city during a specified period
-- e.g. select orderNumber 
--      from Deliver_To
--      where Deliver_To.addrid in (select Address.addrid from Address where city = 'Montreal' ) and TimeDelivered < '2017-03-21' and TimeDelivered > '2016-03-21';


