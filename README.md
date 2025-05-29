# Video Rental Store
A system that allows users to rent videos and allows proper management of video store resources.

## Technologies
![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue?logo=apachemaven&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1-green?logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue?logo=postgresql&logoColor=white)

## Prerequisites
- Java 17+
- Maven
- Optional - API Client (Postman)
- Optional - Database Management Tool

## Installation
1. Clone the repository:
    ```bash
    https://github.com/Esosa2006/Video-Rental-System.git

2. cd Video-Rental-System

3. mvn clean install

4. mvn exec:java -Dexec.mainClass="com.VRS.Video.Rental.System.VideoRentalSystemApplication"

## Usage


## File Structure
```powershell
src/
 ├── main/
 │   ├── java/
 │   │   └── VRS/
 │   │       └── Video/
 │   │           └── Rental/
 │   │               └── System/
 │   │                   ├── VideoRentalSystemApplication.java
 │   │                   ├── configs/
 │   │                   │   └── WebConfig.java
 │   │                   ├── controllers/
 │   │                   │   ├── CustomerController.java
 │   │                   │   └── ManagerController.java
 │   │                   ├── dtos/
 │   │                   │   └── CustomerDto.java
 │   │                   ├── entities/
 │   │                   │   ├── Customer.java
 │   │                   │   ├── StoreManager.java
 │   │                   │   └── Video.java
 │   │                   ├── enums/
 │   │                   │   └── AvailabilityStatus.java
 │   │                   ├── exceptions/
 │   │                   │   ├── Exception.java
 │   │                   │   ├── GlobalExceptionHandler.java
 │   │                   │   └── GlobalRuntimeException.java
 │   │                   ├── mail/
 │   │                   │   └── EmailSenderService.java
 │   │                   ├── mappers/
 │   │                   │   └── CustomerMapper.java
 │   │                   ├── repositories/
 │   │                   │   ├── AvailableVideosRepo.java
 │   │                   │   ├── CustomerRepository.java
 │   │                   │   └── RentedVideosRepo.java
 │   │                   └── services/
 │   │                       ├── CustomerService.java
 │   │                       ├── ManagerService.java
 │   │                       └── StoreService.java
 │   └── resources/
 │       ├── application.yml
 │       ├── static/
 │       └── templates/
```

## Steps to Contribute
Contribute if you want.
1. Open an issue if you're serious.
2. Fork it, clone it.
3. Make a branch:
   ```bash
    git checkout -b your-branch-name
4. Do your thing.
5. Commit and push.
6. Open a pull request.

## License
[MIT](https://choosealicense.com/licenses/mit/)
