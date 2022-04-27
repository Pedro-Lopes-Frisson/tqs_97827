import React, {Component, useState, useEffect} from 'react';
import '../App.css';
import CardDisplay from '../components/card';
import {Form, Container, Card, CardGroup, Row, Badge, Col} from 'react-bootstrap';
import SearchBar from '../components/SearchBar'


function CityList(){
  const [data, setData] = useState({})
  const [cityName, setCityName] = useState("Autauga")
  const [error, setError] = useState("")
  const [numberOfDays, setNumberOfDays] = useState(1)

  const capitalize = (word) => {
    const lower = word.toLowerCase();
    return word.charAt(0).toUpperCase() + lower.slice(1);
  }

  const SearchHelper = () => {
    setError( "Try -> Autauga. Or Maybe there still is no data about this country." )
  }

  const fetchState = async (city_name) => {
    setData({})
    setCityName(city_name)
    const fiveDaysAgo = new Date(new Date().setDate(new Date().getDate() - numberOfDays));
    const fetchItem = await fetch('http://localhost:8000/api/reports/?city='+ city_name +'&date=' + fiveDaysAgo.toISOString().split('T')[0]);
    console.log('http://localhost:8000/api/reports/?city='+ city_name +'&date=' + fiveDaysAgo.toISOString().split('T')[0])
    const item = await fetchItem.json();
    console.log("ITEM -> ", item["data"][0])
    if(item["data"].length==0){
      SearchHelper();
      return;
    }
    setData(item["data"][0])
    console.log(data === {})
  };

  const renderInfo = () => {

     return Object.keys(data)
          .filter( (k) => {
            if (!(k === "date" || k === "last_update" || k === "region" || k.includes("diff")) )
              return k;
          })
          .map(function(k,_) {
            return (
                <Col>
                  <Card id={k}>
                  <Card.Header className="text-center p-2">
                    {k}
                  </Card.Header>
                  <Card.Subtitle className="text-center p-2">
                    {data[k]}
                  </Card.Subtitle>
                  </Card>
                </Col>
            );
          })
  }

    return(
      <Container>
        <Row>
          <h1>
            Cities {cityName}
          </h1>
        </Row>
        <Row>
          <Col>
          <SearchBar handler ={fetchState}/>
          </Col>
          <Col>
            <Form.Select onChange={(event) => {setNumberOfDays(event.target.value)}} aria-label="Default select example">
              <option>Open this select menu</option>
              <option value="0">Today</option>
              <option value="1">Yesterday</option>
              <option value="2">Two Days Ago</option>
            </Form.Select>
          </Col>
        </Row>
        {
           Object.keys(data).length !== 0 ?
            <Row className="m-5" key="dataSection" id="dataSection">{renderInfo(data)}</Row>
              : (error == "") ?
                <p id="NoDataToShow"> There is no data to show </p>
              : <p>{error}</p>

        }
      </Container>
    )
}

export default CityList;
