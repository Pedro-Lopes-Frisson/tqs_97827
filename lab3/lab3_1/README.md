# Lab3_1

## Spring application Testing

1. Identify a couple of examples on the use of AssertJ expressive methods chaining.

EmployeeRestControllerTemplateIT.java
```
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");
```
Chaining of mehtods to assert the format and content of the response given by the RestController.

2. Identify an example in which you mock the behavior of the repository (and avoid involving a database).

In this section the use of mock is clear.
First of with the annotation Mock and respective InjectMocks. After that we see a lot of stubbing that allows
this test to mimic a full fledged database for our study case.
```
    @Mock( lenient = true)
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }
```

3. What is the difference between standard @Mock and @MockBean?
@Mock is usefull for unit testing since Beans are more complex object that have a lifecycle and a Context associated and they are managed by SpringBoot IoC container.
@MockBean can also introduce some different dynamics when testing since that different contexts can lead to problems between tests, those tests are also more time consuming since a bean cannot be reused by another test unless you use Spring's Context Caching mechanisms which also solves different contexts between tests.
It's preferable to use unit Testing when possible instead of using Sliced or full Test enviroments

4. What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?
This file is used to create a different enviroment to run integrations test, for eample our main database can be a Sql database where we need to configure it's properties, but when we are testing we don't need to use a persistent DB so we can create another properties file and use H2Database instead which is faster since it rans on volatile memory.

5. The sample project demonstrates three test strategies to assess an API (C, D and E)developedwith SpringBoot.Which are the main/keydifferences?
It makes sense to divide the code in the set of the layers they use, theres no need to test the database and mocking a RestController or loading it context so what we do is slice or Application to multiple layers and the develop each test with each layer.
