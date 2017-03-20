-- #0
select phoneNumber 
from users
where userid = userid; 
-- java: name variable userid

-- #1
select max(userid)
from users;  
-- java: name max(userid)+1 as new_userid

insert into users
values (new_userid, name, phoneNumber);

insert into bankcard
values (cardNumber, expiryDate, bank);

insert into creditcard
values (cardNumber, new_userid, organization);

-- #2
select *
from product
where name = name and type = type and modelNumber = modelNumber and brand = brand;
-- if  != null

insert into Save_To_Shopping_Cart
values(userid, pid, addTime, quantity);
-- choose (true)
-- java count quantity


-- #3
select *
from Save_To_shopping_Cart
where pid = pid; -- choose to buy

select max(itemid)
from OrderItem; -- java: name max(itemid)+1 as new_itemid
insert into OrderItem
values(new_itemid, pid, price, creationtime);

select max(orderNumber)
from Orders;  
-- name max(orderNumber)+1 as new_orderNumber
insert into Orders(orderNumber, creationTime)
values(new_orderNumber, creationTime);
-- java count quantity
insert into Contain
values(new_orderNumber, itemid, quantity);
-- java calculate totalamount
insert into Orders(totalAmount)
values(totalAmount);

insert into Payment
values(orderNumber, creditcardNumber, payTime);
-- check if there is the orderNumber in Payment
insert into Orders(paymentState)
values(paymentState);


-- #4
select max(addrid)
from address;
-- name max_addrid+1 as new_addrid 
insert into address
values (new_addrid, userid, name, contactPhoneNumber, province, city, streetaddr, postCode);
insert into Deliver_To
values(new_addrid, orderNumber, TimeDelivered);
