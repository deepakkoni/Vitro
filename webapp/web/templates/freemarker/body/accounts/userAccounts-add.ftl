<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#-- Template for adding a user account -->

<h3><a class="account-menu" href="accountsAdmin">User accounts</a> > Add new account</h3>

    <#if errorEmailIsEmpty??>
        <#assign errorMessage = "You must supply an email address." />
    </#if>
    
    <#if errorEmailInUse??>
        <#assign errorMessage = "An account with that email address already exists." />
    </#if>
    
    <#if errorEmailInvalidFormat??>
        <#assign errorMessage = "'${emailAddress}' is not a valid email address." />
    </#if>
    
    <#if errorFirstNameIsEmpty??>
        <#assign errorMessage = "You must supply a first name." />
    </#if>
    
    <#if errorLastNameIsEmpty??>
        <#assign errorMessage = "You must supply a last name." />
    </#if>
    
    <#if errorNoRoleSelected??>
        <#assign errorMessage = "You must select a role." />
    </#if>
    
    <#if errorPasswordIsEmpty??>
        <#assign errorMessage = "No password supplied." />
    </#if>
    
    <#if errorPasswordIsWrongLength??>
        <#assign errorMessage = "Password must be between ${minimumLength} and ${maximumLength} characters." />
    </#if>
    
    <#if errorPasswordsDontMatch??>
        <#assign errorMessage = "Passwords do not match." />
    </#if>
    
    <#if errorMessage?has_content>
        <section id="error-alert" role="alert">
            <img src="${urls.images}/iconAlert.png" width="24" height="24" alert="Error alert icon"/>
            <p>${errorMessage}</p>
        </section>
    </#if>

<section id="add-account" role="region">
    <fieldset>
        <legend>Add new account</legend>

        <form method="POST" action="${formUrls.add}" class="customForm" role="add new account">
            <label for="email-address">Email address<span class="requiredHint"> *</span></label>
            <input type="text" name="emailAddress" value="${emailAddress}" id="email-address" role="input "/>

            <label for="first-name">First name<span class="requiredHint"> *</span></label> 
            <input type="text" name="firstName" value="${firstName}" id="first-name" role="input "/>

            <label for="last-name">Last name<span class="requiredHint"> *</span></label> 
            <input type="text" name="lastName" value="${lastName}" id="last-name" role="input "/>

            <p>Roles<span class="requiredHint"> *</span> </p>
            <#list roles as role>
                <input type="radio" name="role" value="${role.uri}" role="radio" <#if selectedRole = role.uri>selected</#if> />
                <label class="inline" for="${role.label}"> ${role.label}</label>
                <br />
            </#list>

            <#if !emailIsEnabled??>
                <label for="initial-password">Initial password<span class="requiredHint"> *</span></label>
                <input type="password" name="initialPassword" value="${initialPassword}" id="initial-password" role="input "/>

                <p>Minimum of ${minimumLength} characters in length.</p>

                <label for="confirm-password">Confirm initial password<span class="requiredHint"> *</span></label> 
                <input type="text" name="confirmPassword" value="${confirmPassword}" id="confirm-password" role="input "/>
            </#if>

            <p>Associate a profile with this account</p>
            <input type="radio" name="associate" value="yes" role="radio" <#if associate??>checked</#if> id="associate" />
            <label class="inline" for="associate"> Yes</label>

            <input type="radio" name="associate" value="no"  role="radio" <#if !associate??>checked</#if> id="no-associate" />
            <label class="inline" for="no-associate"> No</label>

            <#if emailIsEnabled??>
                <p class="note">
                    Note: An email will be sent to the address entered above 
                    notifying that an account has been created. 
                    It will include instructions for activating the account and creating a password.
                </p>
            </#if>
    
            <input type="submit" name="submitAdd" value="Add new account" class="submit"/> or <a class="cancel" href="${formUrls.list}">Cancel</a>

            <p class="requiredHint">* required fields</p>
        </form>
    </fieldset>
</section>

${stylesheets.add('<link rel="stylesheet" href="${urls.base}/edit/forms/css/customForm.css" />')}