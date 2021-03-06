# Introduction to JUnit 5 with Mockito

All source code examples in the repository are for my [Online Course - Testing Spring Beginner to Guru](https://www.udemy.com/testing-spring-boot-beginner-to-guru/?couponCode=GITHUB_REPO)

This source code repository contains JUnit 5 and Mockito test examples with Maven.

## Setup
### Requirements
* Should use Java 11 or higher. Previous versions of Java are un-tested.
* Use Maven 3.5.2 or higher

#### Mock can be created using two ways - 

1. Creating Mockito Mocks Inline mock(Map.class)
2. Using @Mock annotation. 

Note that the class whose method has to be invoked with Mock object needs to be injected 
in the Test class with @InjectMocks annotation

#### Verify zero or more interactions with Mock 
1. verifyNoMoreInteractions(mockObject)
2. verifyZeroInteractions(mockObject)
