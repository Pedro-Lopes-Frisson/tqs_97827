import React from 'react';
import '../App.css';

import {Navbar, Nav} from 'react-bootstrap'
import {LinkContainer} from 'react-router-bootstrap'

function MainNav(){

  const navStyle={
    textDecoration: 'none'
  };

  return(

        <Navbar bg="light" expand="lg" >
          <LinkContainer to="/">
            <Navbar.Brand>Covid Tracker</Navbar.Brand>
          </LinkContainer>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <LinkContainer to="/">
              <Nav.Link>WorldWide</Nav.Link>
            </LinkContainer>
            <Nav className="mr-auto">
              <LinkContainer to="/citiesList">
                <Nav.Link>City List</Nav.Link>
              </LinkContainer>
            </Nav>
            <Nav className="mr-auto">
              <LinkContainer to="/cache">
                <Nav.Link>Cache</Nav.Link>
              </LinkContainer>
            </Nav>
          </Navbar.Collapse>
        </Navbar>
  );
}



export default MainNav;
