import React from 'react'

export  function ApplyEllipsis(data) {
    
        if(data == null)
            return "Null desc";
        return (data.length>20 )? data.substr(0,20)+'...':data;
    
}
