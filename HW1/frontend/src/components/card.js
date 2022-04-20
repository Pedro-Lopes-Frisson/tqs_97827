import React from 'react';
import '../App.css';
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import { Col, Row, Button, Navbar, Nav, NavItem, NavDropdown, Dropdown, Container } from 'react-bootstrap';
import Plot from 'react-plotly.js';

function CardDisplay(props){

  const navStyle={
    textDecoration: 'none'
  };

  const renderGraph = () => {
    if("dates" in props){
      return (
                 <Plot
                    data={[
                        {
                          x: props.dates,
                          y: props.plotData,
                          type: 'scatter',
                        },
                      ]}
                    layout={{
                        autosize: false,
                        width: 500,
                        height: 500,
                        yaxis: {
                          automargin: true,
                          titlefont: { size:30 },
                        },
                        title:props.title
                    }
                  }
                />
      )
    }
  }

  return(
    <Col xs={2} className="m-2 justify-content-center">
      <Row>
          {renderGraph()}
      </Row>
      <Row>
          <p>
              {props.dValue}
          </p>
      </Row>
    </Col>
  );
}



export default CardDisplay;
