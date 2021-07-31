import React from 'react'
import {Navbar,Nav,Table,Container, NavbarBrand, NavDropdown} from 'react-bootstrap';
import NavbarCollapse from 'react-bootstrap/esm/NavbarCollapse';
import { BrowserRouter, Link, Redirect, Route, Switch } from 'react-router-dom'
import Buglist from './bugform/Buglist';
import Bugform from './bugform/Bugform';
import ProjectInfo from './projectform/ProjectInfo';
import Projectlist from './projectform/Projectlist';
import Projectform from './projectform/Projectform';
import Footer from './footer';
import FooterPage from './footer';

export default function Homepage() {
    return (
        <Container fluid>
            <div >

                <BrowserRouter>

                    <div>
                        <Navbar bg="dark" variant="dark">
                            <Nav style={{ margin: "6px" }}>
                                <Nav.Item >
                                    <img
                                        float="right"
                                        src="/logo.jpg"
                                        width="30"
                                        height="30"
                                        className="d-inline-block align-top"
                                        alt="React Bootstrap logo"
                                    />
                                </Nav.Item>
                            </Nav>
                            <NavbarBrand style={{ marginLeft: "5px" }}>
                                Bug Tracker System
                            </NavbarBrand>


                        </Navbar>
                    </div>
                    <div >
                        <Navbar bg="dark" variant="dark">
                            <NavbarCollapse>
                                <Nav>
                                    <Nav.Item ><Nav.Link href="/projectlist">Project List</Nav.Link></Nav.Item>
                                    <Nav.Item><Nav.Link href="/projectform">Project Form</Nav.Link></Nav.Item>
                                    <Nav.Item> <Nav.Link href="/buglist">Bug List</Nav.Link></Nav.Item>
                                    <Nav.Item> <Nav.Link href="/bugform">Bug Form</Nav.Link></Nav.Item>
                                    <NavDropdown title="About">
                                        <NavDropdown.Item>Company</NavDropdown.Item>
                                        <NavDropdown.Item>Team</NavDropdown.Item>
                                        <NavDropdown.Item>Contact</NavDropdown.Item>
                                    </NavDropdown>
                                </Nav>
                            </NavbarCollapse>
                        </Navbar>
                        <br></br>
                    </div>


                    <Switch>
                    <Route exact path="/">
                            <Redirect to="/projectlist" />
                        </Route>
                        <Route exact path="/projectlist">
                            <Projectlist></Projectlist>
                        </Route>
                        <Route path="/projectform">
                            <Projectform></Projectform>
                        </Route>
                        <Route path="/bugform">
                          <Bugform></Bugform>
                        </Route>
                        <Route path="/buglist">
                            <Buglist></Buglist>
                        </Route>
                        <Route path="/getProject/:projectid">
                            <ProjectInfo></ProjectInfo>
                        </Route>
                    </Switch>
                </BrowserRouter>
                 <FooterPage ></FooterPage>
            </div>
        </Container>
    )
}
