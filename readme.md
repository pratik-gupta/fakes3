# Project Title

Fake S3 demo

## Project Details

The project is build using spring-boot microservice framework with fakeS3 client to windows/unix file system to act as local file system.
Hence this saves calls to AWS S3 and development/testing can be faster.

## Getting Started

### Prerequisites

- Install fakes3 client from location https://rubyinstaller.org/downloads/
- Download rubyinstaller-2.4.4-1-x64.exe [withoud devkit]
- Execute the exe
- run below command to install
	- gem install fakes3
- To run local client run below command. Create folder local-s3 under which you can create buckets.	
	- fakes3 -r C:/local-s3 -p 4567 [windows]
	- fakes3 -r ~/local-s3 -p 8081 [linux]
	
### Build
mvn package

### Deployment
mvn spring-boot:run

## Built With

* [Maven] - Dependency Management
* [Spring boot] - microservice framework
* [AWS SDK]
* [FakeS3]
