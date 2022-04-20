import React, {Component, useState, useEffect} from 'react';
import '../App.css';
import CardDisplay from '../components/card';
import {Container, CardGroup, Row, Badge, Col} from 'react-bootstrap';
import SearchBar from '../components/SearchBar'


function CityList(){
  const [data, setData] = useState([])
  const [compiledList, setCompiledList] = useState({dates: [], cases: [], mortes:[], recovered:[], active:[], fatality:[] });
  const [cityName, setCityName] = useState("Autauga")
  const [error, setError] = useState("")

  const capitalize = (word) => {
    const lower = word.toLowerCase();
    return word.charAt(0).toUpperCase() + lower.slice(1);
  }

  useEffect(() => {
        fetchState(cityName);
    },[])
  const SearchHelper = () => {
    setError( "Try -> Autauga" )
  }

  const fetchState = async (city_name) => {
    setData([])
    setCityName(city_name)
    console.log(cityName)
    setCompiledList((prevState) =>{
        return  {dates:[] ,cases: [],mortes : [], recovered:[], active:[], fatality: []}
      }
    )
    for (let i = 1; i < 10; i++) {
      const fiveDaysAgo = new Date(new Date().setDate(new Date().getDate() - i));
      const fetchItem = await fetch('http://localhost:8000/api/reports/?city='+ city_name +'&date=' + fiveDaysAgo.toISOString().split('T')[0]);
      console.log('http://localhost:8000/api/reports/?city='+ city_name +'&date=' + fiveDaysAgo.toISOString().split('T')[0])
      const item = await fetchItem.json();
      console.log(item)
      if(item["data"].length==0){
        SearchHelper();
      }
      else{
        const item_data = item["data"][0]
        if (!compiledList.dates.includes(item_data.date)) {
            setData(oldArray => [...oldArray, item_data]);
            setCompiledList((prevState) =>{
              let date_push = []
              date_push.push(item_data["date"])
              prevState.dates.forEach((v) => { date_push.push(v)})

              let cases_push = []
              cases_push.push(item_data["confirmed"])
              prevState.cases.forEach((v) => { cases_push.push(v)})

              let recovered_push = []
              recovered_push.push(item_data["recovered"])
              prevState.recovered.forEach((v) => { recovered_push.push(v)})

              let mortes_push = []
              mortes_push.push(item_data["deaths"])
              prevState.mortes.forEach((v) => { mortes_push.push(v)})

              let active_push = []
              active_push.push(item_data["active"])
              prevState.active.forEach((v) => { active_push.push(v)})

              let fatality_push = []
              fatality_push.push(item_data["fatality_rate"])
              prevState.fatality.forEach((v) => { fatality_push.push(v) })


              return  {...prevState,dates:date_push ,cases: cases_push, mortes : mortes_push, recovered:recovered_push, active: active_push, fatality: fatality_push} } )
        }
      }
      }
  };

  const renderInfo = () => {

      return Object.keys(compiledList).filter((k) => {if (!(k === "dates")) return k;}).map( function(k, _) {
        return (
                <Col key={_.toString()} className="justify-content-center text-center">
                  <CardDisplay className="justify-content-center text-center" key={k} title = {capitalize(k) } dates={compiledList.dates} plotData={compiledList[k]} />
                </Col>
        )
      }
    )
  }

    return(
      <Container>
        <Row>
          <h1>
            Cities {cityName}
          </h1>
        </Row>
        <Row>
          <SearchBar handler ={fetchState}/>
        </Row>
        <Row >
        {
          data.length!=0 ?
           renderInfo(compiledList) : (error == "") ?  "" : <p>{error}</p>
        }
        </Row>
      </Container>
    )
}

export default CityList;
