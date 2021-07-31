import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useAlert } from 'react-alert';
import { Col, Form, Row } from 'react-bootstrap';

export default function Bugform() {
    const [bug, setBug] = useState({ name: 'Bug 1', ownerName: 'Archna',
    email:'arcbhart@gamil.com', description: 'this is first bug to solve.',priority: "LOW",status: 'NEW' });
    const alert = useAlert();
    const [project, setProject] = useState([]);

    const onChange = (e) => {
        let url = "http://localhost:8085/api/bug/upload";
        let file = e.target.files[0];
        uploadFile(url, file);
      };
      
      const uploadFile = (url, file) => {
        let formData = new FormData();
        formData.append("file", file);
        axios.post(url, formData, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          }).then((response) => {
            fnSuccess(response);
          }).catch((error) => {
            fnFail(error);
          });
      };
      const fnSuccess = (response) => {
        console.log(response);
      };
      
      const fnFail = (error) => {
        console.log(error);
      };
    useEffect(() => {
        const promise = axios.get(process.env.REACT_APP_BACKEND_URL);
        promise.then(response => response.data).then(projectReturned => {
            setProject(projectReturned);
        })
 
    }, []);
    
   

    const validateName=(content)=>{
        if(content.length<3 || content.length>25){
            return false;
        }
        return true
    }
 
    const validateDescription=(content)=>{
        if(content.length<10 || content.length>250){
            return false;
        }
        return true
    }
 
    const validateEmail=(email)=> {
        const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }
 
    const validations = () => {
        if(!validateName(bug.name)){
            alert.show('Name must be between 3 and 25 characters');
        }
        else if(!validateName(bug.ownerName)){
            alert.show('Owner name must be between 3 and 25 characters');
        }
        else if (!validateDescription(bug.description)) {
            alert.show('Description must be between 10 and 250 characters');
        }
        else if (!validateEmail(bug.email)) {
            alert.show('Invalid email');
        }
        else {
            save();
        }
    }

    const save = () => {
        console.log(process.env.REACT_APP_BACKEND_URL_BUG)

        const promise = axios.post(process.env.REACT_APP_BACKEND_URL_BUG, bug);
        promise.then(response => {  //success
          
            console.log(response);
        })
        promise.catch(error => {
            console.log(error);
        })
        console.log('Saved');
    }
    const handlerChange = (event) => {
        const updatedState = { ...bug, [event.target.name]: event.target.value };
        console.log(updatedState);
        setBug(updatedState);
    }
 return (
    <div style={{ padding: '30px' }}>
            <h3>Bug form</h3><br />
 
            <Form.Group as={Row} className="mb-3" >
                <Form.Label column sm="2">
                    <b>Select Project</b>
                </Form.Label>
                <Col sm="10">
                    <select class="form-select form-select-lg mb-3" aria-label=".form-select-lg example" value={bug.projectId} onChange={handlerChange} name='projectId'>
                        <option selected> PROJECTS</option>
                        {
                            project.map(projects => {
                                return (<option value={projects.id}>{projects.name}</option>)
                            })
                        }
                    </select>
                </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-3" >
                <Form.Label column sm="2">
                    Bug name
                </Form.Label>
                <Col sm="10">
                    <Form.Control value={bug.name} onChange={handlerChange} name="name" placeholder="Enter name" />
                </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-3" >
                <Form.Label column sm="2">
                    Bug description
                </Form.Label>
                <Col sm="10">
                    <Form.Control value={bug.description} onChange={handlerChange} name="description" placeholder="Enter description" />
                </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-3" >
                <Form.Label column sm="2">
                    Owner name
                </Form.Label>
                <Col sm="10">
                    <Form.Control value={bug.ownerName} onChange={handlerChange} name="ownerName" placeholder="Enter owner name" />
                </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-3" >
                <Form.Label column sm="2">
                    Email
                </Form.Label>
                <Col sm="10">
                    <Form.Control type="email" value={bug.email} onChange={handlerChange} name="email" required="required" placeholder="Enter email" />
                </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-3" >
                <Form.Label column sm="2">
                    Priority
                </Form.Label>
                <Col sm="10">
                    <input type="radio" value="HIGH" name="priority" onChange={handlerChange} /> High <br />
                    <input type="radio" value="MEDIUM" name="priority" onChange={handlerChange} /> Medium <br />
                    <input type="radio" value="LOW" name="priority" onChange={handlerChange} defaultChecked /> Low <br />
                </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-3" >
                <Form.Label column sm="2">
                    Status
                </Form.Label>
                <Col sm="10">
                    <input type="radio" value="MEDIUM" name="status" onChange={handlerChange} defaultChecked /> NEW <br />
                    <input type="radio" value="HIGH" name="status" onChange={handlerChange} /> ACTIVE <br />
                    <input type="radio" value="INACTIVE" name="status" onChange={handlerChange} /> INACTIVE <br />
                    <input type="radio" value="FIXED" name="status" onChange={handlerChange} /> FIXED <br />
                    <input type="radio" value="VERIFIED" name="status" onChange={handlerChange} /> VERIFIED <br />
                </Col>
            </Form.Group>
            <button onClick={validations} class="btn btn-primary">Save</button>
            <input type="file" onChange={onChange} accept ="image/*"/>
        </div>
    );
}


