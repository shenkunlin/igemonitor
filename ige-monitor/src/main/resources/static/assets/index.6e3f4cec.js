var x=Object.defineProperty;var a=Object.getOwnPropertySymbols;var $=Object.prototype.hasOwnProperty,D=Object.prototype.propertyIsEnumerable;var i=(e,s,t)=>s in e?x(e,s,{enumerable:!0,configurable:!0,writable:!0,value:t}):e[s]=t,o=(e,s)=>{for(var t in s||(s={}))$.call(s,t)&&i(e,t,s[t]);if(a)for(var t of a(s))D.call(s,t)&&i(e,t,s[t]);return e};import{V as p,n,L as C,T as b}from"./index.a1219e87.js";const k={username:"admin",password:"admin"},S={username:[{required:!0,message:"\u8D26\u53F7\u5FC5\u586B",type:"error"}],password:[{required:!0,message:"\u5BC6\u7801\u5FC5\u586B",type:"error"}]};var F=p.extend({name:"Login",data(){return{FORM_RULES:S,type:"password",formData:o({},k),showPsw:!1}},methods:{async onSubmit({validateResult:e}){e===!0&&(await this.$store.dispatch("user/login",this.formData),this.$message.success("\u767B\u5F55\u6210\u529F"),this.$router.replace("/").catch(()=>""))}}}),f=function(){var e=this,s=e.$createElement,t=e._self._c||s;return t("t-form",{ref:"form",class:["item-container","login-"+e.type],attrs:{data:e.formData,rules:e.FORM_RULES,"label-width":"0"},on:{submit:e.onSubmit}},[t("t-form-item",{attrs:{name:"username"}},[t("t-input",{attrs:{size:"large",placeholder:"\u8BF7\u8F93\u5165\u8D26\u53F7\uFF1Aadmin"},scopedSlots:e._u([{key:"prefix-icon",fn:function(){return[t("t-icon",{attrs:{name:"user"}})]},proxy:!0}]),model:{value:e.formData.username,callback:function(r){e.$set(e.formData,"username",r)},expression:"formData.username"}})],1),t("t-form-item",{attrs:{name:"password"}},[t("t-input",{attrs:{size:"large",type:e.showPsw?"text":"password",clearable:"",placeholder:"\u8BF7\u8F93\u5165\u767B\u5F55\u5BC6\u7801\uFF1Aadmin"},scopedSlots:e._u([{key:"prefix-icon",fn:function(){return[t("t-icon",{attrs:{name:"lock-on"}})]},proxy:!0},{key:"suffix-icon",fn:function(){return[t("t-icon",{attrs:{name:e.showPsw?"browse":"browse-off"},on:{click:function(r){e.showPsw=!e.showPsw}}})]},proxy:!0}]),model:{value:e.formData.password,callback:function(r){e.$set(e.formData,"password",r)},expression:"formData.password"}})],1),t("t-form-item",{staticClass:"btn-container"},[t("t-button",{attrs:{block:"",size:"large",type:"submit"}},[e._v(" \u767B\u5F55 ")])],1)],1)},T=[];f._withStripped=!0;const c={};var _=n(F,f,T,!1,R,null,null,null);function R(e){for(let s in c)this[s]=c[s]}_.options.__file="src/pages/login/components/components-login.vue";var B=function(){return _.exports}();const P={phone:"",email:"",password:"",verifyCode:"",checked:!1},E={phone:[{required:!0,message:"\u624B\u673A\u53F7\u5FC5\u586B",type:"error"}],email:[{required:!0,email:!0,message:"\u90AE\u7BB1\u5FC5\u586B",type:"error"}],password:[{required:!0,message:"\u5BC6\u7801\u5FC5\u586B",type:"error"}],verifyCode:[{required:!0,message:"\u9A8C\u8BC1\u7801\u5FC5\u586B",type:"error"}]};var L=p.extend({name:"Register",data(){return{FORM_RULES:E,type:"phone",emailOptions:[],formData:o({},P),showPsw:!1,countDown:0,intervalTimer:null}},beforeDestroy(){clearInterval(this.intervalTimer)},methods:{onSubmit({validateResult:e}){if(e===!0){if(!this.formData.checked){this.$message.error("\u8BF7\u540C\u610FTDesign\u670D\u52A1\u534F\u8BAE\u548CTDesign \u9690\u79C1\u58F0\u660E");return}this.$message.success("\u6CE8\u518C\u6210\u529F"),this.$emit("registerSuccess")}},switchType(e){this.$refs.form.reset(),this.type=e},handleCounter(){this.countDown=60,this.intervalTimer=setInterval(()=>{this.countDown>0?this.countDown-=1:(clearInterval(this.intervalTimer),this.countDown=0)},1e3)}}}),d=function(){var e=this,s=e.$createElement,t=e._self._c||s;return t("t-form",{ref:"form",class:["item-container","register-"+e.type],attrs:{data:e.formData,rules:e.FORM_RULES,"label-width":"0"},on:{submit:e.onSubmit}},[e.type=="phone"?[t("t-form-item",{attrs:{name:"phone"}},[t("t-input",{attrs:{maxlength:11,size:"large",placeholder:"\u8BF7\u8F93\u5165\u60A8\u7684\u624B\u673A\u53F7"},scopedSlots:e._u([{key:"prefix-icon",fn:function(){return[t("t-icon",{attrs:{name:"user"}})]},proxy:!0}],null,!1,2632148982),model:{value:e.formData.phone,callback:function(r){e.$set(e.formData,"phone",r)},expression:"formData.phone"}})],1)]:e._e(),e.type=="email"?[t("t-form-item",{attrs:{name:"email"}},[t("t-input",{attrs:{type:"text",size:"large",placeholder:"\u8BF7\u8F93\u5165\u60A8\u7684\u90AE\u7BB1"},scopedSlots:e._u([{key:"prefix-icon",fn:function(){return[t("t-icon",{attrs:{name:"mail"}})]},proxy:!0}],null,!1,1078895470),model:{value:e.formData.email,callback:function(r){e.$set(e.formData,"email",r)},expression:"formData.email"}})],1)]:e._e(),t("t-form-item",{attrs:{name:"password"}},[t("t-input",{attrs:{size:"large",type:e.showPsw?"text":"password",clearable:"",placeholder:"\u8BF7\u8F93\u5165\u767B\u5F55\u5BC6\u7801"},scopedSlots:e._u([{key:"prefix-icon",fn:function(){return[t("t-icon",{attrs:{name:"lock-on"}})]},proxy:!0},{key:"suffix-icon",fn:function(){return[t("t-icon",{attrs:{name:e.showPsw?"browse":"browse-off"},on:{click:function(r){e.showPsw=!e.showPsw}}})]},proxy:!0}]),model:{value:e.formData.password,callback:function(r){e.$set(e.formData,"password",r)},expression:"formData.password"}})],1),e.type=="phone"?[t("t-form-item",{staticClass:"verification-code",attrs:{name:"verifyCode"}},[t("t-input",{attrs:{size:"large",placeholder:"\u8BF7\u8F93\u5165\u9A8C\u8BC1\u7801"},model:{value:e.formData.verifyCode,callback:function(r){e.$set(e.formData,"verifyCode",r)},expression:"formData.verifyCode"}}),t("t-button",{attrs:{variant:"outline",disabled:e.countDown>0},on:{click:e.handleCounter}},[e._v(" "+e._s(e.countDown==0?"\u53D1\u9001\u9A8C\u8BC1\u7801":e.countDown+"\u79D2\u540E\u53EF\u91CD\u53D1")+" ")])],1)]:e._e(),t("t-form-item",{staticClass:"check-container",attrs:{name:"checked"}},[t("t-checkbox",{model:{value:e.formData.checked,callback:function(r){e.$set(e.formData,"checked",r)},expression:"formData.checked"}},[e._v("\u6211\u5DF2\u9605\u8BFB\u5E76\u540C\u610F ")]),e._v(" "),t("span",[e._v("TDesign\u670D\u52A1\u534F\u8BAE")]),e._v(" \u548C "),t("span",[e._v("TDesign \u9690\u79C1\u58F0\u660E")])],1),t("t-form-item",[t("t-button",{attrs:{block:"",size:"large",type:"submit"}},[e._v(" \u6CE8\u518C ")])],1),t("div",{staticClass:"switch-container"},[t("span",{staticClass:"tip",on:{click:function(r){return e.switchType(e.type=="phone"?"email":"phone")}}},[e._v(e._s(e.type=="phone"?"\u4F7F\u7528\u90AE\u7BB1\u6CE8\u518C":"\u4F7F\u7528\u624B\u673A\u53F7\u6CE8\u518C"))])])],2)},q=[];d._withStripped=!0;const u={};var v=n(L,d,q,!1,A,null,null,null);function A(e){for(let s in u)this[s]=u[s]}v.options.__file="src/pages/login/components/components-register.vue";var I=function(){return v.exports}(),h=function(){var e=this,s=e.$createElement,t=e._self._c||s;return t("header",{staticClass:"login-header"},[t("logo-full-icon",{staticClass:"logo"}),t("div",{staticClass:"operations-container"},[t("t-button",{attrs:{theme:"default",shape:"square",variant:"text"},on:{click:e.navToGitHub}},[t("t-icon",{staticClass:"icon",attrs:{name:"logo-github"}})],1),t("t-button",{attrs:{theme:"default",shape:"square",variant:"text"},on:{click:e.navToHelper}},[t("t-icon",{staticClass:"icon",attrs:{name:"help-circle"}})],1),t("t-button",{attrs:{theme:"default",shape:"square",variant:"text"},on:{click:e.toggleSettingPanel}},[t("t-icon",{staticClass:"icon",attrs:{name:"setting"}})],1)],1)],1)},z=[];h._withStripped=!0;const M={components:{LogoFullIcon:C},methods:{navToGitHub(){window.open("https://github.com/Tencent/tdesign-vue-starter")},navToHelper(){window.open("https://tdesign.tencent.com/starter/docs/get-started")},toggleSettingPanel(){this.$store.commit("setting/toggleSettingPanel",!0)}}},l={};var g=n(M,h,z,!1,H,"1e452055",null,null);function H(e){for(let s in l)this[s]=l[s]}g.options.__file="src/pages/login/components/components-header.vue";var O=function(){return g.exports}(),U="./assets/video.728437c4.mp4",j="./assets/logo@2x.7ced5e4e.png",w=function(){var e=this,s=e.$createElement,t=e._self._c||s;return t("div",{staticClass:"login-wrapper"},[t("video",{attrs:{autoplay:"",muted:"",loop:"",id:"myVideo"},domProps:{muted:!0}},[t("source",{attrs:{src:U,type:"video/mp4"}})]),t("div",{staticClass:"login-container"},[t("div",{staticClass:"title-container"},[t("h1",{staticClass:"title margin-no"},[t("img",{staticClass:"logo",attrs:{src:j}})])]),e.type==="login"?t("login"):t("register",{on:{"register-success":function(r){return e.switchType("login")}}}),t("tdesign-setting")],1)])},V=[];w._withStripped=!0;const G={name:"LoginIndex",components:{LoginHeader:O,Login:B,Register:I,TdesignSetting:b},data(){return{type:"login"}},methods:{switchType(e){this.type=e}}},m={};var y=n(G,w,V,!1,N,null,null,null);function N(e){for(let s in m)this[s]=m[s]}y.options.__file="src/pages/login/index.vue";var Q=function(){return y.exports}();export{Q as default};
