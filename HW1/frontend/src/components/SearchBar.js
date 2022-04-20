import React from 'react';
import { Col, Row, Button, Form , FormControl } from 'react-bootstrap';


function SearchBar(props) {
    const [cityName, setCityName] = React.useState("Autauga");
    return (
      <div>
        <Row>
        <Col md="10">
            <input
              placeholder="Search"
              className="me-2"
              aria-label="Search"
              onChange={(event) => {setCityName(event.target.value)}}
              onKeyPress={(event) => {if(event.code == "Enter") props.handler(cityName)}}
            />
            <Button variant="outline-success" onClick={ () => {console.log(cityName);props.handler(cityName)}}>Search</Button>
        </Col>
        </Row>
        </div>
    )
}
export default SearchBar;
