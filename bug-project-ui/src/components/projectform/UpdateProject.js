import axios from 'axios';
import React, { useState } from 'react'
import { useEffect } from 'react';
export default function Updateproject(props) {
    const [selectedProject, setselectedProject] = useState(props.sendProject);
    useEffect(() => {
        if (!selectedProject) {
            setselectedProject(selectedProject);
        }
    }, [selectedProject]);
    const update = () => {
        const { createdDate, ...updatedBug } = selectedProject;
        const promise = axios.put(process.env.REACT_APP_BACKEND_URL_PROJECT_UPDATE + selectedProject.id, updatedBug);
        promise.then(response => {
            //  alert(response);
            console.log(response);
        })
        promise.catch(error => {
            console.log(error);
        })
        console.log('updated');
    }
    const handleChangeForselectedProject = (event) => {

        const updatedProjectSpecific = { ...selectedProject, [event.target.name]: event.target.value };
        setselectedProject(updatedProjectSpecific);
    }
    return (
        <div>

            <form>

                <div class="row" style={{ marginTop: "10px", marginBottom: "10px" }}>
                    <label class="col-2">Name</label>
                    <input class="form-control" value={selectedProject.name} onChange={handleChangeForselectedProject} name='name' disabled></input>
                </div>
                <div class="row" style={{ marginTop: "10px", marginBottom: "10px" }}>
                    <label class="col-2">Description</label>
                    <textarea class="form-control" value={selectedProject.description} onChange={handleChangeForselectedProject} name='description'></textarea>
                </div>

            </form>    
                <button onClick={update} class="btn btn-success ">Update</button>
                <button onClick={props.handleClose} class="btn btn-primary" style={{ marginTop: "5px", marginLeft: "10px", textAlign: "right", float: "right" }}>Close</button>

        </div>
    )
}