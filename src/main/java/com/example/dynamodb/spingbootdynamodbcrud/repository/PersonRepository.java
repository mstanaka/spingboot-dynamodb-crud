package com.example.dynamodb.spingbootdynamodbcrud.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.dynamodb.spingbootdynamodbcrud.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Person save(Person person) {
        dynamoDBMapper.save(person);
        return person;
    }

    public Person findById(String id) {
        return dynamoDBMapper.load(Person.class, id);
    }

    public List<Person> findAll() {
        return dynamoDBMapper.scan(Person.class, new DynamoDBScanExpression());
    }

    public Person update(String id, Person person) {
        dynamoDBMapper.save(person, new DynamoDBSaveExpression()
                                        .withExpectedEntry("personId", new ExpectedAttributeValue(
                                                new AttributeValue().withS(id)
                                        )));
        return findById(id);
    }

    public String delete(String id) {
        Person person = findById(id);
        dynamoDBMapper.delete(person);
        return "Person delete successfully:: " + id;
    }
}
