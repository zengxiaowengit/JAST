// import { process, user } from "system";

let tips = {data:[1,2,3 ]};
tips.code = 0;
tips.msg = tips.data[0];

tips.data.push(tips.data[0] + tips.data[1]);


function add(a, b){
    return a + b;
}

tips.data.push(add(8880, 8));

tips.add = (a, b)=>{
    let tmp = a + b;
    tips.data.push(tmp);
}

tips.add(9990, 9);
tips.add(1000, 9);
tips.length = tips.data.length;

tips.add(1000, 1 + 51%2 / 20);
// if else for map filter
// import
// Math.round(), toFixed()
tips.for = []
tips.data.forEach(item =>{
    tips.for.push(item)
})

