import axios from 'axios';
import React, { useState } from 'react'
import { useEffect } from 'react';
export default function UpdateBug(props) {
    const [selectedBug,setSelectedBug] = useState(props.sendBug);

    useEffect(() => {
        if (!selectedBug) {
            setSelectedBug(selectedBug);
        }
    }, [selectedBug]);
    const update = () => {
        const { createddate, ...updatedBug } = selectedBug;
        const promise = axios.put(process.env.REACT_APP_BACKEND_URL_BUG_UPDATE+selectedBug.id,updatedBug);
        promise.then(response => {
          //  alert(response);
            console.log(response);
        })
        promise.catch(error => {
            console.log(error);
        })
        console.log('updated');
       // alert.show("updated");
    }
    
    const handleChangeForSelectedBug = (event) => {

        const updatedBugSpecific = { ...selectedBug, [event.target.name]: event.target.value };
        setSelectedBug(updatedBugSpecific);
    }
    return (
        <div>
            <h3></h3>
            <form>
               

                <div class="row">
                    <div class="row" style={{marginBottom:"10px"}}>
                    <label class="col-2">Name</label>
                        <input class="form-control" cass="col-10" value={selectedBug.name} onChange={handleChangeForSelectedBug} name='name' disabled></input>
                    </div>
                    <div class="row" style={{marginBottom:"10px"}}>
                        <label class="col-2">OwnerName</label>
                        <input class="form-control"  value={selectedBug.ownerName} onChange={handleChangeForSelectedBug} name='ownerName' disabled></input>
                    </div>
                    <div class="row" style={{marginBottom:"10px"}}>
                    <label class="col-2">Description</label>
                        <textarea class="form-control" value={selectedBug.description} onChange={handleChangeForSelectedBug} name='description'></textarea>
                    </div>
                    <div class="row" style={{marginBottom:"10px"}}>
                    <label class="col-2">Email</label>
                        <input class="form-control" value={selectedBug.email} onChange={handleChangeForSelectedBug} name='email' disabled></input>
                    </div>

                </div>
               
            </form>
            <button onClick={update} class="btn btn-success ">Update</button>
            <button onClick={props.handleClose} class="btn btn-primary" style={{ marginTop: "5px", marginLeft: "10px", textAlign: "right", float: "right" }}>Close</button>
        </div>
    )
}