import React, {Component, useState, useEffect} from 'react';
import '../App.css';
import CardDisplay from '../components/card';
import {Container, CardGroup, Row, Badge, Col} from 'react-bootstrap';
import SearchBar from '../components/SearchBar'


function CacheStatistics(){
  const [data, setData] = useState({})
  const [error, setError] = useState("")

  const capitalize = (word) => {
    const lower = word.toLowerCase();
    return word.charAt(0).toUpperCase() + lower.slice(1);
  }

  useEffect(() => {
        fetchState();
    },[])
  const SearchHelper = () => {
    setError( "Try -> Autauga" )
  }

  const fetchState = async () => {
    const fetch_request = await fetch("http://localhost:8000/api/cache")
    const response_json = await fetch_request.json()
    console.log(response_json)
    setData(response_json)
  }


    return(
      <Container>
        <Row>
          <h1>
            Cache Api Statistics
          </h1>
        </Row>
        <Row>
          <Col>
            <p>Requests</p>
            <p>{data["requests"]}</p>
          </Col>
          <Col>
            <p>Misses</p>
            <p>{data["misses"]}</p>
          </Col>
          <Col>
            <p>Hits</p>
            <p>{data["hits"]}</p>
          </Col>
        </Row>
      </Container>
    )
}

export default CacheStatistics;
