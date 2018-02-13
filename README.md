# Online-Shopping-Cart-Database-Project

You may find more details about this project at http://www.aaronguan.com/database-project.html

## ER Diagram

![image](http://www.aaronguan.com/images/database/ER.PNG)

### Entities

* User (<u>userId</u>, name, phoneNum)
* Buyer (userId)
* Seller (userId)
* Bank Card (cardNumber, userId, bank, expiryDate)
* Credit Card (cardNumber, organization)
* Debit Card (cardNumber)
* Store (sid, name, startTime, customerGrade, streetAddr, city, province)
* Product (pid, sid, name, brand, type, amount, price, colour, customerReview, modelNumber)
* Order Item (itemid, pid, price, creationTime)
* Order (orderNumber, creationTime, paymentStatus, totalAmount)
* Address (addrid, userid, name, city, postalCode, streetAddr, province, contactPhoneNumber)
## Create Database

[Table.sql](https://github.com/aaronzguan/Online-Shopping-Cart-Database-Project/blob/master/Table.sql): create tables
