import React, { Component } from 'react';
import './App.css';
import Home from './pages/home';
import CityList from './pages/cityList';
import MainNav from './components/nav';
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
function App() {








  return (
    <Router>
      <MainNav />
			<Routes>
				<Route path='/' exact element={<Home />} />
				<Route path='/cityList' exact element={<CityList />} />
			</Routes>
		</Router>

  );
}

export default App;
