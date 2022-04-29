import React, { Component } from 'react';
import './App.css';
import Home from './pages/home';
import CityList from './pages/cityList';
import CacheStatistics from './pages/cacheStatistics';
import MainNav from './components/nav';
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
function App() {
  return (
    <Router>
      <MainNav />
			<Routes>
				<Route path='/world' exact element={<Home />} />
				<Route path='/' exact element={<CityList />} />
				<Route path='/cache' exact element={<CacheStatistics />} />
			</Routes>
		</Router>

  );
}

export default App;
