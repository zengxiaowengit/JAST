// import { process, user } from "system";

let tips = {data:[1,2,3 ]};
tips.code = 0;
tips.msg = tips.data[0];
tips.data = tips.data + 2;
//tips.data += 2;


tips.data.push(tips.data[0] + tips.data[1]);


function add(a, b){
    return a + b;
}

tips.data.push(add(100, 2));
