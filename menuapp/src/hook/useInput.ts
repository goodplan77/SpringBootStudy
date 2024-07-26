import { ChangeEvent, useState } from "react";

const useInput = <T>(init:any) => {
    let [obj , setObj]= useState<T>(init);

    const onInputChange = (e:ChangeEvent) => {
        let {name , value} = e.target as HTMLInputElement;
        setObj({
            ...obj,
            [name] : value
        })
    }
    return [obj , onInputChange] as const; // 각 인덱스마다 타입 지정?
}
export default useInput;