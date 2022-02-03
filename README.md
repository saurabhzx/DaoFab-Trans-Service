# DoaFab-Trans-Service
This is the DoaFab-Trans-Service having the parent and child transactions


Steps to Run 

1. Simply Build and run the product with gradle or import it in the IntelliJ IDE

./gradlew bootRun  (This to run the Spring boot stand alone web app)

2. Run the DaofabServiceApplication java file as stand alone application


API to fetch parent details:- 

http://127.0.0.1:9008/daofab-service/api/internal/v1/daofab-parent-transaction/data?page_no=2

API to fetch the CHILD detail by parent ID

http://127.0.0.1:9008/daofab-service/api/internal/v1/daofab-child-transaction/data?parent_id=1
