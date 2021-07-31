import axios from 'axios';
import React, { useState } from 'react'
import { useAlert } from 'react-alert';
import { Col, Form, Row } from 'react-bootstrap';

export default function Projectform() {
 
    const [project, setProject] = useState({name:"City Project",description:"This is good Project"});
    const save=()=>{
        console.log(process.env.REACT_APP_BACKEND_URL)
       const promise= axios.post(process.env.REACT_APP_BACKEND_URL,project);
       promise.then(response=>{  //sucess
        
           console.log(response);
       })
       promise.catch(error=>{
           console.log(error);
       })
        console.log('Saved');
    }

    const handlerChange=(event)=>{
          const updatedState= {...project,[event.target.name]: event.target.value};
          console.log(updatedState);
          setProject(updatedState);
             }

const alert = useAlert();
 
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
 
    const validations = () => {
        if(!validateName(project.name)){
            alert.show('Name must be between 3 and 25 characters');
        }
        else if (!validateDescription(project.description)) {
            alert.show('Description must be between 10 and 250 characters');
        }
        else {
            save()
        }
    }
 
    return (
        <div style={{ padding: '30px' }}>
            <h3>Project form</h3><br/>
            <Form.Group as={Row} className="mb-3" >
                <Form.Label column sm="2">
                    Project name
                </Form.Label>
                <Col sm="10">
                    <Form.Control value={project.name} onChange={handlerChange} name="name" placeholder="Enter name" />
                </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-3" >
                <Form.Label column sm="2">
                    Project description
                </Form.Label>
                <Col sm="10" >
                    <Form.Control value={project.description} onChange={handlerChange} name="description" placeholder="Enter description" />
                </Col>
            </Form.Group>
            <button onClick={validations} class="btn btn-primary">Save</button>
        </div>
    );

 }
