# Project Title

Fake S3 demo

## Project Details

The project is build using spring-boot microservice framework with fakeS3 client to windows/unix file system to act as local file system.
Hence this saves calls to AWS S3 and development/testing can be faster. It supports same implementation of S3 calls as java SDK [put,list,get].

## Getting Started

### Prerequisites

- Install fakes3 client from location https://rubyinstaller.org/downloads/
- Download rubyinstaller-2.4.4-1-x64.exe [withoud devkit]
- Execute the exe
- run below command to install
	- gem install fakes3
- To run local client run below command. Create folder s3.localhost under which you can create buckets.	
	- fakes3 -r C:/s3.localhost -p 4567 [windows]
	- fakes3 -r ~/s3.localhost -p 8081 [linux]
	
### Build
mvn package

### Deployment
mvn spring-boot:run

- To use fake S3 you need to put the file first via code, as it drops a file in folder like structure. So in localhost it created a folder with the key and inside it keeps content. But irrespective of structure list, read works as is just by passing key name for object

## Built With

* [Maven] - Dependency Management
* [Spring boot] - microservice framework
* [AWS SDK]
* [FakeS3]
